package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.comment.CommentRequest;
import kg.nurtelecom.opinion.payload.comment.CommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    Page<CommentResponse> getAllComments(Long articleId);
    ResponseEntity<CommentResponse> saveComment(Long articleId, CommentRequest commentRequest, User user);
    ResponseEntity<CommentResponse> replyToComment(Long id, CommentRequest commentRequest, User user);
    ResponseEntity<CommentResponse> updateCommentById(Long id, CommentRequest commentRequest);
    void deleteCommentById(Long id);
}
