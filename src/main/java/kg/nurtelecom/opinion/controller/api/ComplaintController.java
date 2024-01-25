package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kg.nurtelecom.opinion.payload.complaint.ComplaintDTO;
import kg.nurtelecom.opinion.service.ComplaintService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/complaints/on-article")
@Tag(
        name = "Контроллер для создания жалоб",
        description = "В этом контроллере описаны на создание жалоб"
)
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping("/{id}")
    @Operation(
            summary = "Создание статьи"
    )
    public ResponseEntity<ComplaintDTO> createComplain(@PathVariable("id") Long id,
                                                       @RequestBody @Valid ComplaintDTO complaintDTO){
        System.out.println(complaintDTO);
        return complaintService.createComplaint(id, complaintDTO);
    }
}
