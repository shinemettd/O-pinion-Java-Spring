package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kg.nurtelecom.opinion.payload.complain.ComplainDTO;
import kg.nurtelecom.opinion.service.ComplainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/complaints/on-article")
@Tag(
        name = "Контроллер для создания жалоб",
        description = "В этом контроллере описаны на создание жалоб"
)
public class ComplainController {

    private final ComplainService complainService;

    public ComplainController(ComplainService complainService) {
        this.complainService = complainService;
    }

    @PostMapping("/{id}")
    @Operation(
            summary = "Создание статьи"
    )
    public ResponseEntity<ComplainDTO> createComplain(@PathVariable("id") Long id,
                                            @RequestBody @Valid ComplainDTO complainDTO){
        System.out.println(complainDTO);
        return complainService.createComplain(id, complainDTO);
    }
}
