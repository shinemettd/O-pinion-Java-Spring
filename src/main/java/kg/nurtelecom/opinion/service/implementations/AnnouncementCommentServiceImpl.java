package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Announcement;
import kg.nurtelecom.opinion.entity.AnnouncementComment;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.AccessType;
import kg.nurtelecom.opinion.enums.Role;
import kg.nurtelecom.opinion.exception.NoAccessException;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.mapper.AnnouncementCommentMapper;
import kg.nurtelecom.opinion.payload.announcement_comment.AnnouncementCommentRequest;
import kg.nurtelecom.opinion.payload.announcement_comment.AnnouncementCommentResponse;
import kg.nurtelecom.opinion.repository.AnnouncementCommentRepository;
import kg.nurtelecom.opinion.repository.AnnouncementRepository;
import kg.nurtelecom.opinion.repository.UserRepository;
import kg.nurtelecom.opinion.service.AnnouncementCommentService;
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
public class AnnouncementCommentServiceImpl implements AnnouncementCommentService {

    private final AnnouncementCommentRepository announcementCommentRepository;
    private final AnnouncementRepository announcementRepository;
    private final AnnouncementCommentMapper announcementCommentMapper;
    private final UserRepository userRepository;
    private final UserNotificationService userNotificationService;
    @Value("${client-application.route.user}")
    private String userRoute;
    @Value("${client-application.route.announcement}")
    private String announcementRoute;

    public AnnouncementCommentServiceImpl(AnnouncementCommentRepository announcementCommentRepository, AnnouncementRepository announcementRepository, AnnouncementCommentMapper announcementCommentMapper, UserRepository userRepository, UserNotificationService userNotificationService) {
        this.announcementCommentRepository = announcementCommentRepository;
        this.announcementRepository = announcementRepository;
        this.announcementCommentMapper = announcementCommentMapper;
        this.userRepository = userRepository;
        this.userNotificationService = userNotificationService;
    }

    @Override
    public ResponseEntity<AnnouncementCommentResponse> getComment(Long commentId, User user) {
        AnnouncementComment announcementComment = announcementCommentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id " + commentId + " не найден"));

        Role userRole = (user != null) ? user.getRole() : Role.ROLE_USER;
        AccessType announcementAccessType = announcementComment.getAnnouncement().getAccessType();

        if (announcementAccessType.equals(AccessType.EMPLOYEES) && userRole.equals(Role.ROLE_USER)) {
            throw new NoAccessException("У вас нет доступа к объявлениям для работников");
        }

        AnnouncementCommentResponse commentResponse = announcementCommentMapper.toModel(announcementComment);

        return ResponseEntity.ok(commentResponse);
    }

    @Override
    public ResponseEntity<Page<AnnouncementCommentResponse>> getAnnouncementComments(Long announcementId, Pageable pageable, User user) {
        Role userRole = (user != null) ? user.getRole() : Role.ROLE_USER;
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new NotFoundException("Статья с id " + announcementId + " не найдена"));
        AccessType announcementAccessType = announcement.getAccessType();

        if (announcementAccessType.equals(AccessType.EMPLOYEES) && userRole.equals(Role.ROLE_USER)) {
            throw new NoAccessException("У вас нет доступа к объявлениям для работников");
        }

        Page<AnnouncementComment> announcementComments = announcementCommentRepository.findComments(announcementId, pageable);
        Page<AnnouncementCommentResponse> commentResponses = announcementComments.map(announcementCommentMapper::toModel);

        return ResponseEntity.ok(commentResponses);
    }

    @Override
    public ResponseEntity<Long> getTotalComments(Long announcementId) {
        return new ResponseEntity<>(announcementCommentRepository.countByAnnouncementId(announcementId),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AnnouncementCommentResponse> saveComment(Long announcementId, AnnouncementCommentRequest announcementCommentRequest, User user) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new NotFoundException("Статья с id " + announcementId + " не найдена"));

        AccessType announcementAccessType = announcement.getAccessType();
        Role userRole = (user != null) ? user.getRole() : Role.ROLE_USER;

        if (announcementAccessType.equals(AccessType.EMPLOYEES) && userRole.equals(Role.ROLE_USER)) {
            throw new NoAccessException("У вас нет доступа к объявлениям для работников");
        }

        AnnouncementComment announcementComment = announcementCommentMapper.toEntity(announcementCommentRequest);
        String replacedText = announcementComment.getText().replaceAll("@(\\w+)", "<a href=\"" + userRoute + "/$1" + "\"><strong>@$1</strong></a>");
        announcementComment.setText(replacedText);
        announcementComment.setDate(LocalDateTime.now());
        announcementComment.setUser(user);
        announcementComment.setAnnouncement(announcement);
        announcementComment.setEdited(false);

        AnnouncementComment savedComment = announcementCommentRepository.save(announcementComment);

        String text = announcementCommentRequest.text();
        List<String> mentionedUsers = extractMentionedUsers(text);
        for (String nickname : mentionedUsers) {
            Optional<User> mentioned = userRepository.findByNickname(nickname);
            if (mentioned.isPresent()) {
                String notificationContent = constructMentionNotificationContent(announcementId, announcementCommentRequest.text(), user);
                String url = announcementRoute + "/" + announcementId;
                userNotificationService.createUserNotification("Вас упомянули в комментарии", notificationContent, mentioned.get(), url);
            }
        }


        return ResponseEntity
                .status(HttpStatus.CREATED).body(announcementCommentMapper.toModel(savedComment));
    }

    @Override
    public ResponseEntity<AnnouncementCommentResponse> updateCommentById(Long commentId,
                                                                         AnnouncementCommentRequest announcementCommentRequest,
                                                                         User user) {
        AnnouncementComment announcementComment = findCommentById(commentId);

        AccessType announcementAccessType = announcementComment.getAnnouncement().getAccessType();
        Role userRole = (user != null) ? user.getRole() : Role.ROLE_USER;

        if (announcementAccessType.equals(AccessType.EMPLOYEES) && userRole.equals(Role.ROLE_USER)) {
            throw new NoAccessException("У вас нет доступа к объявлениям для работников");
        }

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

        AccessType announcementAccessType = announcementComment.getAnnouncement().getAccessType();
        Role userRole = (user != null) ? user.getRole() : Role.ROLE_USER;

        if (announcementAccessType.equals(AccessType.EMPLOYEES) && userRole.equals(Role.ROLE_USER)) {
            throw new NoAccessException("У вас нет доступа к объявлениям для работников");
        }

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

    private List<String> extractMentionedUsers(String content) {
        Pattern pattern = Pattern.compile("@(\\w+)");
        Matcher matcher = pattern.matcher(content);
        List<String> mentionedUsers = new ArrayList<>();
        while (matcher.find()) {
            mentionedUsers.add(matcher.group(1));
        }
        return mentionedUsers;
    }

    private String constructMentionNotificationContent(Long announcementId, String commentContent, User user) {
        String content = "<p>Пользователь <a href=\"[[user_url]]\"><strong>[[nickname]]</strong></a> упомянул(-а) вас в комментарии под объявлением." +
                " Содержание: " + "\"" + commentContent.substring(0, Math.min(commentContent.length(), 30)) + "\"" +
                " Нажмите на уведомление, чтобы узнать подробнее.</p>";
        content = content.replace("[[user_url]]", userRoute + "/" + user.getNickname());
        content = content.replace("[[nickname]]", user.getNickname());
        return content;
    }
}
