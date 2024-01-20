package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.nurtelecom.opinion.payload.article.TagDTO;
import kg.nurtelecom.opinion.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/tags")
@Tag(
        name = "Теги",
        description = "Контроллер для получения всех тегов"
)
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    @Operation(
            summary = "Получение всех тегов"
    )
    public ResponseEntity<List<TagDTO>> getAllTags(){
        return tagService.getAll();
    }
}
