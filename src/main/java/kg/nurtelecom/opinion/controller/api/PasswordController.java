package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.nurtelecom.opinion.payload.user.PasswordResetRequest;
import kg.nurtelecom.opinion.service.PasswordService;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/forgot")
    public ResponseEntity<?> forgotPassword(
            @RequestParam(name = "email") String email,
            HttpServletRequest servletRequest
    ) {
        return passwordService.requestPasswordResetToken(email, servletRequest);
    }

    @PutMapping("/reset/{token}")
    public void resetPassword(
            @PathVariable("token") String passwordResetToken,
            @Valid @RequestBody PasswordResetRequest passwordResetRequest
    ) {
        passwordService.updatePassword(passwordResetToken, passwordResetRequest);
    }
}
