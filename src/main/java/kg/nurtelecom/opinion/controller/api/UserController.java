package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.mapper.UserMapper;
import kg.nurtelecom.opinion.payload.user.*;
import kg.nurtelecom.opinion.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/users")
@Tag(
        name = "Контроллер для управления пользователями "
)
public class UserController {
    private final UserService userService;

    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/my-profile")
    @Operation(
            summary = "Получение моего профиля "
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<GetUserResponse> getMyProfile(@AuthenticationPrincipal User user) {
        return userService.getMyProfile(user);
    }

    @GetMapping("/{id}/profile")
    @Operation(
            summary = "Получение профиля другого пользователя по его id "
    )
    public ResponseEntity<GetUserProfileDTO> getUserProfile(@PathVariable("id") Long userId) {
        return userService.getUserProfile(userId);
    }

    @DeleteMapping("/delete-account")
    @Operation(
            summary = "Удаление своего аккаунта"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> deleteUserAccount(@AuthenticationPrincipal User user) {
        return userService.deleteUserAccount(user);
    }

    @PutMapping("/restore-account")
    @Operation(
            summary = "Восстановление своего аккаунта"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> restoreUserAccount(@AuthenticationPrincipal User user) {
        return userService.restoreUserAccount(user);
    }

    @PutMapping("/change-data")
    @Operation(
            summary = "Изменение личных данных пользователя (first name, last name, nickname, birth date)"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<GetUserResponse> changeUserData(@AuthenticationPrincipal User user,
                                                          @Valid @RequestBody UserUpdateRequest userRequest) {
        return userService.updateUser(user.getId(), userRequest);
    }
    @PutMapping("/change-email")
    @Operation(
            summary = "Изменение почты пользователя"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<UserUpdateEmailResponse> changeUserEmail(@AuthenticationPrincipal User user,
                                                                  @Valid @RequestBody UserUpdateEmailRequest userRequest) {
        return userService.updateUserEmail(user.getId(), userRequest);
    }
}
