package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.ArticleComment;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.exception.ExceedsNestingLevelException;
import kg.nurtelecom.opinion.exception.NoAccessException;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.mapper.ArticleCommentMapper;
import kg.nurtelecom.opinion.payload.comment.CommentRequest;
import kg.nurtelecom.opinion.payload.comment.CommentResponse;
import kg.nurtelecom.opinion.payload.comment.ReplyCommentResponse;
import kg.nurtelecom.opinion.repository.ArticleCommentRepository;
import kg.nurtelecom.opinion.repository.ArticleRepository;
import kg.nurtelecom.opinion.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {
    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleRepository articleRepository;
    private final ArticleCommentMapper articleCommentMapper;

    public CommentServiceImpl(ArticleCommentRepository articleCommentRepository, ArticleRepository articleRepository, ArticleCommentMapper articleCommentMapper) {
        this.articleCommentRepository = articleCommentRepository;
        this.articleRepository = articleRepository;
        this.articleCommentMapper = articleCommentMapper;
    }

    @Override
    public ResponseEntity<Page<CommentResponse>> getRootComments(Long articleId, Pageable pageable) {
        Page<ArticleComment> comments = articleCommentRepository.findRootComments(articleId, pageable);
        Page<CommentResponse> commentResponses = comments.map(articleCommentMapper::toModel);
        return ResponseEntity.ok(commentResponses);
    }

    @Override
    public ResponseEntity<Page<ReplyCommentResponse>> getCommentReplies(Long id, Pageable pageable) {
        Page<ArticleComment> comments = articleCommentRepository.findCommentReplies(id, pageable);
        Page<ReplyCommentResponse> commentResponses = comments.map(articleCommentMapper::toReplyModel);
        return ResponseEntity.ok(commentResponses);
    }

    @Override
    public ResponseEntity<CommentResponse> saveComment(Long articleId, CommentRequest commentRequest, User user) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new NotFoundException("Статья с id " + articleId + " не найдена"));

        ArticleComment comment = articleCommentMapper.toEntity(commentRequest);
        comment.setDate(LocalDateTime.now());
        comment.setUser(user);
        comment.setArticle(article);
        comment.setAltered(false);
        comment.setDepth(0);

        ArticleComment savedComment = articleCommentRepository.save(comment);

        return ResponseEntity
                .status(HttpStatus.CREATED).body(articleCommentMapper.toModel(savedComment));
    }

    @Override
    public ResponseEntity<CommentResponse> replyToComment(Long id, CommentRequest commentRequest, User user) {
        ArticleComment comment = findCommentById(id);
        checkNestingLevel(comment);

        ArticleComment replyComment = articleCommentMapper.toEntity(commentRequest);
        replyComment.setDate(LocalDateTime.now());
        replyComment.setUser(user);
        replyComment.setArticle(comment.getArticle());
        replyComment.setAltered(false);
        replyComment.setParentComment(comment);
        replyComment.setDepth(comment.getDepth() + 1);

        ArticleComment savedComment = articleCommentRepository.save(replyComment);

        return ResponseEntity
                .status(HttpStatus.CREATED).body(articleCommentMapper.toModel(savedComment));
    }

    @Override
    public ResponseEntity<CommentResponse> updateCommentById(Long id, CommentRequest commentRequest, User user) {
        ArticleComment comment = findCommentById(id);
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new NoAccessException("Комментарий другого пользователя не может быть изменен");
        }

        comment.setText(commentRequest.text());
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

    private ArticleComment findCommentById(Long id) {
        return articleCommentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Комментарий с id " + id + " не найден"));
    }

    private void checkNestingLevel(ArticleComment comment) {
        if (comment.getDepth() > 0) {
            throw new ExceedsNestingLevelException("Нельзя ответить на дочерний комментарий");
        }
    }
}
