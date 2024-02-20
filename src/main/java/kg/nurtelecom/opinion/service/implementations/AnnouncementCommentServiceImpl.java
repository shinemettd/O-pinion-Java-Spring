package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Announcement;
import kg.nurtelecom.opinion.entity.AnnouncementComment;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.AccessType;
import kg.nurtelecom.opinion.exception.NoAccessException;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.mapper.AnnouncementCommentMapper;
import kg.nurtelecom.opinion.payload.announcement_comment.AnnouncementCommentRequest;
import kg.nurtelecom.opinion.payload.announcement_comment.AnnouncementCommentResponse;
import kg.nurtelecom.opinion.repository.AnnouncementCommentRepository;
import kg.nurtelecom.opinion.repository.AnnouncementRepository;
import kg.nurtelecom.opinion.service.AnnouncementCommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AnnouncementCommentServiceImpl implements AnnouncementCommentService {

    private final AnnouncementCommentRepository announcementCommentRepository;
    private final AnnouncementRepository announcementRepository;
    private final AnnouncementCommentMapper announcementCommentMapper;

    public AnnouncementCommentServiceImpl(AnnouncementCommentRepository announcementCommentRepository, AnnouncementRepository announcementRepository, AnnouncementCommentMapper announcementCommentMapper) {
        this.announcementCommentRepository = announcementCommentRepository;
        this.announcementRepository = announcementRepository;
        this.announcementCommentMapper = announcementCommentMapper;
    }

    @Override
    public ResponseEntity<Page<AnnouncementCommentResponse>> getComments(Long announcementId, Pageable pageable) {
        Page<AnnouncementComment> comments = announcementCommentRepository.findComments(announcementId, pageable);
        Page<AnnouncementCommentResponse> commentResponses = comments.map(announcementCommentMapper::toModel);
        return ResponseEntity.ok(commentResponses);
    }

    @Override
    public ResponseEntity<AnnouncementCommentResponse> saveComment(Long announcementId, AnnouncementCommentRequest announcementCommentRequest, User user) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new NotFoundException("Статья с id " + announcementId + " не найдена"));

        AnnouncementComment announcementComment = announcementCommentMapper.toEntity(announcementCommentRequest);
        announcementComment.setDate(LocalDateTime.now());
        announcementComment.setUser(user);
        announcementComment.setAnnouncement(announcement);
        announcementComment.setEdited(false);

        AnnouncementComment savedComment = announcementCommentRepository.save(announcementComment);

        return ResponseEntity
                .status(HttpStatus.CREATED).body(announcementCommentMapper.toModel(savedComment));
    }

    @Override
    public ResponseEntity<AnnouncementCommentResponse> updateCommentById(Long commentId,
                                                                         AnnouncementCommentRequest announcementCommentRequest,
                                                                         User user) {

        AnnouncementComment announcementComment = findCommentById(commentId);

        if (!announcementComment.getUser().getId().equals(user.getId())) {
            throw new NoAccessException("Комментарий другого пользователя не может быть изменен");
        }

        announcementComment.setText(announcementCommentRequest.text());
        announcementComment.setDate(LocalDateTime.now());
        announcementComment.setEdited(true);

        AnnouncementComment savedComment = announcementCommentRepository.save(announcementComment);
        return ResponseEntity.ok(announcementCommentMapper.toModel(savedComment));
    }

    @Override
    public ResponseEntity<Void> deleteCommentById(Long commentId, User user) {
        AnnouncementComment announcementComment = findCommentById(commentId);

        if (!announcementComment.getUser().getId().equals(user.getId())) {
            throw new NoAccessException("Комментарий другого пользователя не может быть удален");
        }

        announcementCommentRepository.delete(announcementComment);
        return ResponseEntity.noContent().build();
    }

    private AnnouncementComment findCommentById(Long commentId) {
        return announcementCommentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id " + commentId + " не найден"));
    }
}
