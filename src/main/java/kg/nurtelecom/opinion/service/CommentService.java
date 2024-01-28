package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.comment.CommentRequest;
import kg.nurtelecom.opinion.payload.comment.CommentResponse;
import kg.nurtelecom.opinion.payload.comment.ReplyCommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<Page<CommentResponse>> getRootComments(Long articleId, Pageable pageable);
    ResponseEntity<Page<ReplyCommentResponse>> getCommentReplies(Long id, Pageable pageable);
    ResponseEntity<CommentResponse> saveComment(Long articleId, CommentRequest commentRequest, User user);
    ResponseEntity<CommentResponse> replyToComment(Long id, CommentRequest commentRequest, User user);
    ResponseEntity<CommentResponse> updateCommentById(Long id, CommentRequest commentRequest, User user);
    void deleteCommentById(Long id, User user);
}
