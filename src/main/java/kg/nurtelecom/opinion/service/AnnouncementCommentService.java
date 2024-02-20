package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.announcement_comment.AnnouncementCommentRequest;
import kg.nurtelecom.opinion.payload.announcement_comment.AnnouncementCommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface AnnouncementCommentService {

    ResponseEntity<Page<AnnouncementCommentResponse>> getComments(Long announcementId, Pageable pageable, User user);
    ResponseEntity<AnnouncementCommentResponse> saveComment(Long announcementId, AnnouncementCommentRequest announcementCommentRequest, User user);
    ResponseEntity<AnnouncementCommentResponse> updateCommentById(Long commentId, AnnouncementCommentRequest announcementCommentRequest, User user);
    ResponseEntity<Void> deleteCommentById(Long commentId, User user);
}
