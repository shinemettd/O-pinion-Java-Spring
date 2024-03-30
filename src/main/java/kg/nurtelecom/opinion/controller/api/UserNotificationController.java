package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.notification.UserNotificationResponse;
import kg.nurtelecom.opinion.service.UserNotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-notifications")
@Tag(
        name = "Уведомления пользователей",
        description = "Контроллер для получения всех уведомлений пользователя"
)
public class UserNotificationController {
    private final UserNotificationService userNotificationService;

    public UserNotificationController(UserNotificationService userNotificationService) {
        this.userNotificationService = userNotificationService;
    }

    @GetMapping
    @Operation(
            summary = "Получение всех уведомлений пользователя"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Page<UserNotificationResponse>> getAllUserNotifications(@PageableDefault(page = 0, size = 10, sort = "dateTime") Pageable pageable,
                                                                                  @AuthenticationPrincipal User user) {
        return userNotificationService.getAllByUser(user, pageable);
    }

    @GetMapping("/not-read")
    @Operation(
            summary = "Получение всех непрочитанных уведомлений пользователя"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Page<UserNotificationResponse>> getAllNotReadUserNotifications(@PageableDefault(page = 0, size = 10, sort = "dateTime") Pageable pageable,
                                                                                  @AuthenticationPrincipal User user) {
        return userNotificationService.getAllNotReadByUser(user, pageable);
    }

    @GetMapping("/read")
    @Operation(
            summary = "Получение всех прочитанных уведомлений пользователя"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Page<UserNotificationResponse>> getAllReadUserNotifications(@PageableDefault(page = 0, size = 10, sort = "dateTime") Pageable pageable,
                                                                                         @AuthenticationPrincipal User user) {
        return userNotificationService.getAllReadByUser(user, pageable);
    }

    @GetMapping("/not-read-count")
    @Operation(
            summary = "Получение количества непрочитанных уведомлений"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Long> getNotReadNotificationsCount(@AuthenticationPrincipal User user) {
        return userNotificationService.getNotReadNotificationsCount(user);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление уведомления пользователя"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> deleteUserNotification(@PathVariable("id") Long id,
                                                       @AuthenticationPrincipal User user) {
        return userNotificationService.deleteUserNotification(id, user);
    }

    @PutMapping("/{id}/make-read")
    @Operation(
            summary = "Сделать уведомление пользователя прочтенным"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<UserNotificationResponse> makeUserNotificationRead(@PathVariable("id") Long id,
                                                                             @AuthenticationPrincipal User user) {
        return userNotificationService.makeUserNotificationRead(id, user);
    }

    @DeleteMapping("/all")
    @Operation(
            summary = "Удаление всех уведомлений пользователя"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> deleteAllUserNotifications(@AuthenticationPrincipal User user) {
        return userNotificationService.deleteAllUserNotifications(user);
    }

    @PutMapping("/all/make-read")
    @Operation(
            summary = "Сделать все уведомления пользователя прочтенными"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> makeAllUserNotificationsRead(@AuthenticationPrincipal User user) {
        return userNotificationService.makeAllUserNotificationsRead(user);
    }
}
