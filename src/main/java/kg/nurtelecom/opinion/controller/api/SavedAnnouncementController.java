package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.announcement.AnnouncementResponse;
import kg.nurtelecom.opinion.service.SavedAnnouncementsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/saved-announcements")
@Tag(
        name = "Контроллер для избранных статей",
        description = "В этом контроллере есть возможности добавления, удаления статей из избранных"
)
public class SavedAnnouncementController {
    private final SavedAnnouncementsService savedAnnouncementsService;


    public SavedAnnouncementController(SavedAnnouncementsService savedAnnouncementsService) {
        this.savedAnnouncementsService = savedAnnouncementsService;
    }


    @GetMapping
    @Operation(
            summary = "Получение всех избранных"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Page<AnnouncementResponse>> getSavedAnnouncements(@PageableDefault(page = 0, size = 10) Pageable pageable,
                                                                            @AuthenticationPrincipal User user) {
        return savedAnnouncementsService.getSavedAnnouncements(user, pageable);
    }

    @PostMapping("/{announcement-id}")
    @Operation(
            summary = "Добавление статьи в избранное по айди"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> addAnnouncementToSaved(@PathVariable("announcement-id") Long announcement_id,
                                                  @AuthenticationPrincipal User user) {
        return savedAnnouncementsService.addAnnouncementToSaved(announcement_id, user);
    }

    @DeleteMapping("/{announcement-id}")
    @Operation(
            summary = "Удаление статьи из избранных по айди"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> deleteAnnouncementFromSaved(@PathVariable("announcement-id") Long announcement_id,
                                                       @AuthenticationPrincipal User user) {
        return savedAnnouncementsService.deleteAnnouncementFromSaved(announcement_id, user);
    }
}
