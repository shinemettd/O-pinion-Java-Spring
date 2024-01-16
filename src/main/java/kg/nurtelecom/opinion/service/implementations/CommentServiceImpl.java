package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.ArticleComment;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.CommentStatus;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.mapper.ArticleCommentMapper;
import kg.nurtelecom.opinion.payload.comment.CommentRequest;
import kg.nurtelecom.opinion.payload.comment.CommentResponse;
import kg.nurtelecom.opinion.repository.ArticleCommentRepository;
import kg.nurtelecom.opinion.repository.ArticleRepository;
import kg.nurtelecom.opinion.service.CommentService;
import org.springframework.data.domain.Page;
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
    public Page<CommentResponse> getAllComments(Long articleId) {
        return null;
    }

    @Override
    public ResponseEntity<CommentResponse> saveComment(Long articleId, CommentRequest commentRequest, User user) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new NotFoundException("Статья с id " + articleId + " не найдена"));

        ArticleComment comment = articleCommentMapper.toEntity(commentRequest);
        comment.setDate(LocalDateTime.now());
        comment.setUser(user);
        comment.setArticle(article);
        comment.setStatus(CommentStatus.ACTIVE);

        ArticleComment savedComment = articleCommentRepository.save(comment);

        return ResponseEntity
                .status(HttpStatus.CREATED).body(articleCommentMapper.toModel(savedComment));
    }

    @Override
    public ResponseEntity<CommentResponse> replyToComment(Long id, CommentRequest commentRequest, User user) {
        ArticleComment comment = articleCommentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Комментарий с id " + id + " не найден"));

        ArticleComment replyComment = articleCommentMapper.toEntity(commentRequest);
        replyComment.setDate(LocalDateTime.now());
        replyComment.setUser(user);
        replyComment.setArticle(comment.getArticle());
        replyComment.setStatus(CommentStatus.ACTIVE);
        replyComment.setParentComment(comment);

        ArticleComment savedComment = articleCommentRepository.save(replyComment);

        return ResponseEntity
                .status(HttpStatus.CREATED).body(articleCommentMapper.toModel(savedComment));
    }

    @Override
    public ResponseEntity<CommentResponse> updateCommentById(Long id, CommentRequest commentRequest) {
        ArticleComment comment = articleCommentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Комментарий с id " + id + " не найден"));

        comment.setText(commentRequest.text());
        comment.setDate(LocalDateTime.now());
        comment.setStatus(CommentStatus.ALTERED);

        ArticleComment savedComment = articleCommentRepository.save(comment);
        return ResponseEntity.ok(articleCommentMapper.toModel(savedComment));

    }

    @Override
    public void deleteCommentById(Long id) {
        ArticleComment comment = articleCommentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Комментарий с id " + id + " не найден"));

        comment.setStatus(CommentStatus.DELETED);
        articleCommentRepository.save(comment);
    }
}
