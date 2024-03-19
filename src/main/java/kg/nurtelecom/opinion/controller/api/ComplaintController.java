package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.complaint.ComplaintDTO;
import kg.nurtelecom.opinion.service.ComplaintService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/complaints")
@Tag(
        name = "Контроллер для создания жалоб",
        description = "В этом контроллере описаны на создание жалоб"
)
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping("/{article-id}")
    @Operation(
            summary = "Добавление жалобы"
    )
    public ResponseEntity<ComplaintDTO> createComplaint(@PathVariable("article-id") Long articleId,
                                                       @Valid @RequestBody ComplaintDTO complaintDTO,
                                                       @AuthenticationPrincipal User user){
        return complaintService.createComplaint(articleId, complaintDTO, user);
    }
}
