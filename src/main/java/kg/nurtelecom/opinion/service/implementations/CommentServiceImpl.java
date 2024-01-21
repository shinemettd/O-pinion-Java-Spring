package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.ArticleComment;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.exception.NotValidException;
import kg.nurtelecom.opinion.mapper.ArticleCommentMapper;
import kg.nurtelecom.opinion.payload.comment.*;
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
    public ResponseEntity<Page<CommentView>> getRootComments(Long articleId, Pageable pageable) {
        Page<CommentView> comments = articleCommentRepository
                .findRootComments(articleId, pageable);

        return ResponseEntity.ok(comments);
    }

    @Override
    public ResponseEntity<Page<CommentRepliesView>> getCommentReplies(Long id, Pageable pageable) {
        Page<CommentRepliesView> replies = articleCommentRepository.findCommentReplies(id, pageable);
        return ResponseEntity.ok(replies);
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

        ArticleComment savedComment = articleCommentRepository.save(comment);

        return ResponseEntity
                .status(HttpStatus.CREATED).body(articleCommentMapper.toModel(savedComment));
    }

    @Override
    public ResponseEntity<CommentResponse> replyToComment(Long id, CommentRequest commentRequest, User user) {
        ArticleComment comment = findCommentById(id);

        ArticleComment replyComment = articleCommentMapper.toEntity(commentRequest);
        replyComment.setDate(LocalDateTime.now());
        replyComment.setUser(user);
        replyComment.setArticle(comment.getArticle());
        replyComment.setAltered(false);
        replyComment.setParentComment(comment);

        ArticleComment savedComment = articleCommentRepository.save(replyComment);

        return ResponseEntity
                .status(HttpStatus.CREATED).body(articleCommentMapper.toModel(savedComment));
    }

    @Override
    public ResponseEntity<CommentResponse> updateCommentById(Long id, CommentRequest commentRequest, User user) {
        ArticleComment comment = findCommentById(id);
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new NotValidException("The other user's comment can't be changed");
        }

        comment.setText(commentRequest.text());
        comment.setDate(LocalDateTime.now());
        comment.setAltered(true);

        ArticleComment savedComment = articleCommentRepository.save(comment);
        return ResponseEntity.ok(articleCommentMapper.toModel(savedComment));
    }

    @Override
    public void deleteCommentById(Long id, User user) {
        ArticleComment comment = findCommentById(id);

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new NotValidException("The other user's comment can't be deleted");
        }
        articleCommentRepository.delete(comment);
    }

    private ArticleComment findCommentById(Long id) {
        return articleCommentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Комментарий с id " + id + " не найден"));
    }
}
