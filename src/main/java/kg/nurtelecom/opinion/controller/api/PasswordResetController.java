package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import kg.nurtelecom.opinion.payload.user.PasswordResetRequest;
import kg.nurtelecom.opinion.payload.user.PasswordResetTokenResponse;
import kg.nurtelecom.opinion.service.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
@CrossOrigin(origins = "*")
@Tag(
        name = "Контроллер для сброса пароля"
)
public class PasswordResetController {
    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @GetMapping("/forgot")
    public ResponseEntity<PasswordResetTokenResponse> forgotPassword(
            @RequestParam(name="email") String email,
            HttpServletRequest servletRequest
    ) {
        return passwordResetService.getPasswordResetToken(email, servletRequest);
    }

    @PutMapping("/reset/{token}")
    public void resetPassword(
            @PathVariable("token") String passwordResetToken,
            @RequestBody PasswordResetRequest passwordResetRequest
    ) {
        passwordResetService.updatePassword(passwordResetToken, passwordResetRequest);
    }
}
