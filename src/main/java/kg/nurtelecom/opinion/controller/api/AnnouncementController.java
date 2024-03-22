package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.announcement.AnnouncementResponse;
import kg.nurtelecom.opinion.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/announcements")
@Tag(
        name = "Контроллер для get операций над объявлениями"
)
public class AnnouncementController {

    private final AnnouncementService service;

    @Autowired
    public AnnouncementController(AnnouncementService announcementService) {
        this.service = announcementService;
    }

    @GetMapping()
    @Operation(
            summary = "Получение всех объявлений"
    )
    public ResponseEntity<Page<AnnouncementResponse>> getAnnouncements(@PageableDefault(page = 0, size = 10, sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable,
                                                                       @AuthenticationPrincipal User user) {
        return service.getAnnouncements(pageable, user);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение опеределенного объявления по его ID"
    )
    public ResponseEntity<AnnouncementResponse> getAnnouncement(@PathVariable("id") Long id,
                                                                @AuthenticationPrincipal User user) {
        return service.getAnnouncement(id, user);
    }


    @GetMapping("/{id}/share")
    @Operation(
            summary = "Поделиться объявлением "
    )
    public ResponseEntity<String> shareAnnouncement(@PathVariable("id") Long articleId,
                                               @RequestParam(value = "share-type", defaultValue = "announcement") String shareType
    ) {
        return service.shareAnnouncement(articleId, shareType);
    }

    @GetMapping("/{id}/share/email")
    @Operation(
            summary = "Поделиться объявлением по почте "
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> shareArticleByEmail(@PathVariable("id") Long articleId,
                                                    @RequestParam(value = "to", required = true) @NotBlank @Email String to,
                                                    @AuthenticationPrincipal User user) {
        if(user != null) {
            return service.shareAnnouncementByEmail(articleId, to, user.getNickname());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
