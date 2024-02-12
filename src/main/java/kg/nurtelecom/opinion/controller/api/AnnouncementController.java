package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.announcement.AnnouncementGetDTO;
import kg.nurtelecom.opinion.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Page<AnnouncementGetDTO>> getAnnouncements(@PageableDefault(page = 0, size = 10, sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable,
                                                                     @AuthenticationPrincipal User user) {
        return service.getAnnouncements(pageable, user);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение опеределенного объявления по его ID"
    )
    public ResponseEntity<AnnouncementGetDTO> getAnnouncement(@PathVariable("id") Long id,
                                                              @AuthenticationPrincipal User user) {
        return service.getAnnouncement(id, user);
    }

}
