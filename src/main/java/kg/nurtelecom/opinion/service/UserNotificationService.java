package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.notification.UserNotificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UserNotificationService {
    ResponseEntity<Void> deleteUserNotification(Long id, User user);

    ResponseEntity<UserNotificationResponse> makeUserNotificationRead(Long id, User user);

    ResponseEntity<Void> deleteAllUserNotifications(User user);

    ResponseEntity<Page<UserNotificationResponse>> makeAllUserNotificationsRead(User user, Pageable pageable);

    void createUserNotification(String title, String content, User user, String url);

    ResponseEntity<Page<UserNotificationResponse>> getAllByUser(User user, Pageable pageable);

    ResponseEntity<Page<UserNotificationResponse>> getAllNotReadByUser(User user, Pageable pageable);

    ResponseEntity<Page<UserNotificationResponse>> getAllReadByUser(User user, Pageable pageable);
}
