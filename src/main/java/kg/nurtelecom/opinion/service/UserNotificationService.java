package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.notification.UserNotificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UserNotificationService {
    ResponseEntity<Page<UserNotificationResponse>> getAll(Pageable pageable);

    ResponseEntity<Void> deleteUserNotification(Long id, User user);

    ResponseEntity<UserNotificationResponse> makeUserNotificationRead(Long id, User user);

    ResponseEntity<Void> deleteAllUserNotifications(User user);

    ResponseEntity<Page<UserNotificationResponse>> makeAllUserNotificationsRead(User user);
}
