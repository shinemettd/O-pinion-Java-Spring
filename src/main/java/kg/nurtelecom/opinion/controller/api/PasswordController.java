package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.password.PasswordResetRequest;
import kg.nurtelecom.opinion.payload.password.PasswordUpdateRequest;
import kg.nurtelecom.opinion.service.PasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
@Tag(name = "Контроллер для управления паролем пользователя"
)
public class PasswordController {
    private final PasswordService passwordService;

    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @PutMapping
    @Operation(summary = "Изменение пароля пользователя")
    public ResponseEntity<Void> updatePassword(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest
    ) {
        return passwordService.updatePassword(user, passwordUpdateRequest);
    }

    @GetMapping("/forgot")
    @Operation(summary = "Отправка ссылки для сброса пароля на указанную почту")
    public ResponseEntity<Void> forgotPassword(
            @RequestParam(name = "email") String email,
            HttpServletRequest servletRequest
    ) {
        return passwordService.requestResetToken(email, servletRequest);
    }

    @PostMapping("/{token}")
    @Operation(summary = "Валидация токена для сброса пароля")
    public ResponseEntity<Void> validateResetToken(@PathVariable("token") String passwordResetToken) {
        return passwordService.validateResetToken(passwordResetToken);
    }

    @PutMapping("/reset/{token}")
    @Operation(summary = "Сброс пароля пользователя")
    public ResponseEntity<Void> resetPassword(
            @PathVariable("token") String passwordResetToken,
            @Valid @RequestBody PasswordResetRequest passwordResetRequest
    ) {
        return passwordService.resetPassword(passwordResetToken, passwordResetRequest);
    }
}
