package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.privacy.UserPrivacySettingsRequest;
import kg.nurtelecom.opinion.service.UserPrivacyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/privacy")
@CrossOrigin(origins = "*")
@Tag(
        name = "Контроллер для контроля приватности профиля пользователя"
)
public class UserPrivacyController {
    private final UserPrivacyService userPrivacyService;

    public UserPrivacyController(UserPrivacyService userPrivacyService) {
        this.userPrivacyService = userPrivacyService;
    }

    @PutMapping("/change")
    @Operation(
            summary = "Создание статьи "
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> changeUserPrivacy(@RequestBody UserPrivacySettingsRequest userPrivacySettings,
                                                  @AuthenticationPrincipal User user) {
        return userPrivacyService.changePrivacy(userPrivacySettings, user);
    }
}
