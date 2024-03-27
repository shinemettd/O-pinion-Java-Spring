package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.entity.UserNotification;
import kg.nurtelecom.opinion.exception.NoAccessException;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.mapper.UserNotificationMapper;
import kg.nurtelecom.opinion.payload.notification.UserNotificationResponse;
import kg.nurtelecom.opinion.repository.UserNotificationRepository;
import kg.nurtelecom.opinion.service.UserNotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserNotificationServiceImpl implements UserNotificationService {
    private final UserNotificationRepository userNotificationRepository;
    private final UserNotificationMapper userNotificationMapper;

    public UserNotificationServiceImpl(UserNotificationRepository userNotificationRepository, UserNotificationMapper userNotificationMapper) {
        this.userNotificationRepository = userNotificationRepository;
        this.userNotificationMapper = userNotificationMapper;
    }

    @Override
    public ResponseEntity<Void> deleteUserNotification(Long id, User user) {
        UserNotification userNotification = userNotificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Уведомление с таким айди не найдено"));

        if (!user.getId().equals(userNotification.getUser().getId())) {
            throw new NoAccessException("У вас нет доступа к данному уведомлению");
        }

        userNotificationRepository.delete(userNotification);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<UserNotificationResponse> makeUserNotificationRead(Long id, User user) {
        UserNotification userNotification = userNotificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Уведомление с таким айди не найдено"));

        if (!user.getId().equals(userNotification.getUser().getId())) {
            throw new NoAccessException("У вас нет доступа к данному уведомлению");
        }

        userNotification.setRead(true);
        userNotificationRepository.save(userNotification);

        UserNotificationResponse userNotificationResponse = userNotificationMapper.toUserNotificationResponse(userNotification);
        return ResponseEntity.ok(userNotificationResponse);
    }

    @Override
    public ResponseEntity<Void> deleteAllUserNotifications(User user) {
        userNotificationRepository.deleteAllByUserId(user.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Page<UserNotificationResponse>> makeAllUserNotificationsRead(User user, Pageable pageable) {
        Page<UserNotification> userNotifications = userNotificationRepository.findAllByUserId(user.getId(), pageable);

        for (UserNotification notification : userNotifications.getContent()) {
            notification.setRead(true);
        }

        userNotificationRepository.saveAll(userNotifications);

        Page<UserNotificationResponse> userNotificationResponsePage = userNotificationMapper.toUserNotificationResponsePage(userNotifications);
        return ResponseEntity.ok(userNotificationResponsePage);
    }

    @Override
    public void createUserNotification(String title, String content, User user, String url) {
        UserNotification userNotification = new UserNotification(title, content, url,false, user);
        userNotificationRepository.save(userNotification);
    }

    @Override
    public ResponseEntity<Page<UserNotificationResponse>> getAllByUser(User user, Pageable pageable) {
        Page<UserNotification> userNotificationPage = userNotificationRepository.findAllByUserId(user.getId(), pageable);
        Page<UserNotificationResponse> userNotificationResponsePage = userNotificationMapper.toUserNotificationResponsePage(userNotificationPage);
        return ResponseEntity.ok(userNotificationResponsePage);
    }

    @Override
    public ResponseEntity<Page<UserNotificationResponse>> getAllNotReadByUser(User user, Pageable pageable) {
        Page<UserNotification> userNotificationPage = userNotificationRepository.findAllByUserIdAndIsReadFalse(user.getId(), pageable);
        Page<UserNotificationResponse> userNotificationResponsePage = userNotificationMapper.toUserNotificationResponsePage(userNotificationPage);
        return ResponseEntity.ok(userNotificationResponsePage);
    }

    @Override
    public ResponseEntity<Page<UserNotificationResponse>> getAllReadByUser(User user, Pageable pageable) {
        Page<UserNotification> userNotificationPage = userNotificationRepository.findAllByUserIdAndIsReadTrue(user.getId(), pageable);
        Page<UserNotificationResponse> userNotificationResponsePage = userNotificationMapper.toUserNotificationResponsePage(userNotificationPage);
        return ResponseEntity.ok(userNotificationResponsePage);
    }
}
