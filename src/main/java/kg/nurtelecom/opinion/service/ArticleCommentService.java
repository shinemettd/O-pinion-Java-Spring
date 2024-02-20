package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.article_comment.ArticleCommentRequest;
import kg.nurtelecom.opinion.payload.article_comment.ArticleCommentResponse;
import kg.nurtelecom.opinion.payload.article_comment.ArticleReplyCommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ArticleCommentService {
    ResponseEntity<Page<ArticleCommentResponse>> getRootComments(Long articleId, Pageable pageable);
    ResponseEntity<Page<ArticleReplyCommentResponse>> getCommentReplies(Long id, Pageable pageable);
    ResponseEntity<ArticleCommentResponse> saveComment(Long articleId, ArticleCommentRequest articleCommentRequest, User user);
    ResponseEntity<ArticleCommentResponse> replyToComment(Long id, ArticleCommentRequest articleCommentRequest, User user);
    ResponseEntity<ArticleCommentResponse> updateCommentById(Long id, ArticleCommentRequest articleCommentRequest, User user);
    ResponseEntity<Void> deleteCommentById(Long id, User user);
}
