package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.announcement_comment.AnnouncementCommentRequest;
import kg.nurtelecom.opinion.payload.announcement_comment.AnnouncementCommentResponse;
import kg.nurtelecom.opinion.service.AnnouncementCommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/announcement-comments")
@Tag(
        name = "Контроллер для управления комментариями объявлений",
        description = "В этом контроллере описаны запросы на CRUD методы"
)
public class AnnouncementCommentController {

    private final AnnouncementCommentService service;

    public AnnouncementCommentController(AnnouncementCommentService service) {
        this.service = service;
    }

    @GetMapping("/{announcement-id}")
    @Operation(
            summary = "Получение всех комментариев объявления по id объявления"
    )
    public ResponseEntity<Page<AnnouncementCommentResponse>> getComments(
            @PathVariable("announcement-id") Long announcementId,
            @PageableDefault Pageable pageable,
            @AuthenticationPrincipal User user) {
        return service.getAnnouncementComments(announcementId, pageable, user);
    }

    @GetMapping("/{announcement-id}/total-comments")
    @Operation(
            summary = "Возвращает количество комментариев статьи по ее id"
    )
    public ResponseEntity<Long> getTotalComments(@PathVariable("announcement-id") Long announcementId) {
        return service.getTotalComments(announcementId);
    }

    @GetMapping("/comment/{comment-id}")
    @Operation(
            summary = "Получение комментария по его id"
    )
    public ResponseEntity<AnnouncementCommentResponse> getComment(
            @PathVariable("comment-id") Long commentId,
            @AuthenticationPrincipal User user) {
        return service.getComment(commentId, user);
    }

    @PostMapping("/{announcement-id}")
    @Operation(
            summary = "Отправка комментария к объявлению по id объявления"
    )
    public ResponseEntity<AnnouncementCommentResponse> saveComment(
            @PathVariable("announcement-id") Long announcementId,
            @Valid @RequestBody AnnouncementCommentRequest request,
            @AuthenticationPrincipal User user
    ) {
        return service.saveComment(announcementId, request, user);
    }

    @PutMapping("/{comment-id}")
    @Operation(
            summary = "Изменение комментария по его id"
    )
    public ResponseEntity<AnnouncementCommentResponse> updateComment(
            @PathVariable("comment-id") Long commentId,
            @Valid @RequestBody AnnouncementCommentRequest request,
            @AuthenticationPrincipal User user
    ) {
        return service.updateCommentById(commentId, request, user);
    }

    @DeleteMapping("/{comment-id}")
    @Operation(
            summary = "Удаление комментария по его id"
    )
    public ResponseEntity<Void> deleteComment(
            @PathVariable("comment-id") Long commentId,
            @AuthenticationPrincipal User user
    ) {
        return service.deleteCommentById(commentId, user);
    }
}
