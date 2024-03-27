package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.ArticleComment;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.exception.ExceedsNestingLevelException;
import kg.nurtelecom.opinion.exception.NoAccessException;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.mapper.ArticleCommentMapper;
import kg.nurtelecom.opinion.payload.article_comment.ArticleCommentRequest;
import kg.nurtelecom.opinion.payload.article_comment.ArticleCommentResponse;
import kg.nurtelecom.opinion.payload.article_comment.ArticleNestedCommentResponse;
import kg.nurtelecom.opinion.repository.ArticleCommentRepository;
import kg.nurtelecom.opinion.repository.ArticleRepository;
import kg.nurtelecom.opinion.repository.UserRepository;
import kg.nurtelecom.opinion.service.ArticleCommentService;
import kg.nurtelecom.opinion.service.UserNotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ArticleCommentServiceImpl implements ArticleCommentService {
    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleRepository articleRepository;
    private final ArticleCommentMapper articleCommentMapper;
    private final UserNotificationService userNotificationService;
    private final UserRepository userRepository;
    @Value(value = "${client-application.host}")
    private String clientApplicationHost;
    @Value("${client-application.route.user}")
    private String userRoute;
    @Value("${client-application.route.article}")
    private String articleRoute;

    public ArticleCommentServiceImpl(ArticleCommentRepository articleCommentRepository, ArticleRepository articleRepository, ArticleCommentMapper articleCommentMapper, UserNotificationService userNotificationService, UserRepository userRepository) {
        this.articleCommentRepository = articleCommentRepository;
        this.articleRepository = articleRepository;
        this.articleCommentMapper = articleCommentMapper;
        this.userNotificationService = userNotificationService;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<Page<ArticleNestedCommentResponse>> getAllComments(Long articleId, Pageable pageable) {
        Page<ArticleComment> comments = articleCommentRepository.findByArticle_IdAndParentCommentIsNull(articleId, pageable);
        Page<ArticleNestedCommentResponse> commentResponses = comments.map(articleCommentMapper::toNestedModel);
        return ResponseEntity.ok(commentResponses);
    }

    @Override
    public ResponseEntity<ArticleCommentResponse> saveComment(Long articleId, ArticleCommentRequest articleCommentRequest, User user) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new NotFoundException("Статья с id " + articleId + " не найдена"));

        ArticleComment comment = articleCommentMapper.toEntity(articleCommentRequest);
        String replacedText = comment.getText().replaceAll("@(\\w+)", "<a href=\"" + userRoute + "/$1" + "\"><strong>@$1</strong></a>");
        comment.setText(replacedText);
        comment.setDate(LocalDateTime.now());
        comment.setUser(user);
        comment.setArticle(article);
        comment.setAltered(false);
        comment.setDepth(0);

        ArticleComment savedComment = articleCommentRepository.save(comment);

        String text = articleCommentRequest.text();
        List<String> mentionedUsers = extractMentionedUsers(text);
        for (String nickname : mentionedUsers) {
            Optional<User> mentioned = userRepository.findByNickname(nickname);
            if (mentioned.isPresent()) {
                String notificationContent = constructMentionNotificationContent(articleId, articleCommentRequest.text(), user);
                String url = articleRoute + "/" + articleId;
                userNotificationService.createUserNotification("Вас упомянули в комментарии", notificationContent, mentioned.get(), url);
            }
        }

        String content = constructCommentNotificationContent(articleId, user, clientApplicationHost);
        String url = articleRoute + "/" + articleId;
        userNotificationService.createUserNotification("Оставлен комментарий под статьей", content, article.getAuthor(), url);

        return ResponseEntity
                .status(HttpStatus.CREATED).body(articleCommentMapper.toModel(savedComment));
    }

    @Override
    public ResponseEntity<ArticleCommentResponse> replyToComment(Long id, ArticleCommentRequest articleCommentRequest, User user) {
        ArticleComment comment = findCommentById(id);
        checkNestingLevel(comment);

        ArticleComment replyComment = articleCommentMapper.toEntity(articleCommentRequest);
        String replacedText = replyComment.getText().replaceAll("@(\\w+)", "<a href=\"" + userRoute + "/$1" + "\"><strong>@$1</strong></a>");
        replyComment.setText(replacedText);
        replyComment.setDate(LocalDateTime.now());
        replyComment.setUser(user);
        replyComment.setArticle(comment.getArticle());
        replyComment.setAltered(false);
        replyComment.setParentComment(comment);
        replyComment.setDepth(comment.getDepth() + 1);

        ArticleComment savedComment = articleCommentRepository.save(replyComment);

        String text = articleCommentRequest.text();
        List<String> mentionedUsers = extractMentionedUsers(text);
        for (String nickname : mentionedUsers) {
            Optional<User> mentioned = userRepository.findByNickname(nickname);
            if (mentioned.isPresent()) {
                String notificationContent = constructMentionNotificationContent(replyComment.getArticle().getId(), articleCommentRequest.text(), user);
                String url = articleRoute + "/" + replyComment.getArticle().getId();
                userNotificationService.createUserNotification("Вас упомянули в комментарии", notificationContent, mentioned.get(), url);
            }
        }

        return ResponseEntity
                .status(HttpStatus.CREATED).body(articleCommentMapper.toModel(savedComment));
    }

    @Override
    public ResponseEntity<ArticleCommentResponse> updateCommentById(Long id, ArticleCommentRequest articleCommentRequest, User user) {
        ArticleComment comment = findCommentById(id);
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new NoAccessException("Комментарий другого пользователя не может быть изменен");
        }

        comment.setText(articleCommentRequest.text());
        comment.setDate(LocalDateTime.now());
        comment.setAltered(true);

        ArticleComment savedComment = articleCommentRepository.save(comment);
        return ResponseEntity.ok(articleCommentMapper.toModel(savedComment));
    }

    @Override
    public ResponseEntity<Void> deleteCommentById(Long id, User user) {
        ArticleComment comment = findCommentById(id);

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new NoAccessException("Комментарий другого пользователя не может быть удален");
        }
        articleCommentRepository.delete(comment);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Long> getTotalComments(Long id) {
        Long totalCommentsValue = articleCommentRepository.countByArticleId(id);
        return ResponseEntity.ok(totalCommentsValue);
    }

    private ArticleComment findCommentById(Long id) {
        return articleCommentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Комментарий с id " + id + " не найден"));
    }

    private void checkNestingLevel(ArticleComment comment) {
        if (comment.getDepth() > 0) {
            throw new ExceedsNestingLevelException("Нельзя ответить на дочерний комментарий");
        }
    }

    private String constructCommentNotificationContent(Long articleId, User user, String host) {
        String content = "<p>Пользователь <a href=\"[[user_url]]\"><strong>[[nickname]]</strong></a> написал(-а) комментарий под вашей статьей." +
                " Нажмите на уведомление, чтобы узнать подробнее.</p>";
        content = content.replace("[[user_url]]", userRoute + "/" + user.getNickname());
        content = content.replace("[[nickname]]", user.getNickname());
        return content;
    }

    private String constructMentionNotificationContent(Long articleId, String commentContent, User user) {
        String content = "<p>Пользователь <a href=\"[[user_url]]\"><strong>[[nickname]]</strong></a> упомянул(-а) вас в комментарии под статьей." +
                " Содержание: " + "\"" + commentContent.substring(0, Math.min(commentContent.length(), 30)) + "\"" +
                " Нажмите на уведомление, чтобы узнать подробнее.</p>";
        content = content.replace("[[user_url]]", userRoute + "/" + user.getNickname());
        content = content.replace("[[nickname]]", user.getNickname());
        return content;
    }

    private List<String> extractMentionedUsers(String content) {
        Pattern pattern = Pattern.compile("@(\\w+)");
        Matcher matcher = pattern.matcher(content);
        List<String> mentionedUsers = new ArrayList<>();
        while (matcher.find()) {
            mentionedUsers.add(matcher.group(1));
        }
        return mentionedUsers;
    }
}
