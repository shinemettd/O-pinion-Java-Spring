package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kg.nurtelecom.opinion.payload.tag.TagRequest;
import kg.nurtelecom.opinion.payload.tag.TagResponse;
import kg.nurtelecom.opinion.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
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
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Page<TagResponse>> getAllTags(@PageableDefault(page = 0, size = 10, sort = "name") Pageable pageable){

        return tagService.getAll(pageable);
    }

    @PostMapping
    @Operation(
            summary = "Создание тега "
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> createTag(@RequestBody @Valid TagRequest tagRequest) {
        return tagService.createTag(tagRequest);
    }
    @GetMapping("/search")
    @Operation(
            summary = "Найти теги с таким именем "
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Page<TagResponse>> findTagsByName(@PageableDefault(page = 0, size = 10, sort = "name") Pageable pageable,
                                                            @RequestParam("name") String tagName) {
        return tagService.findTagsByName(tagName, pageable);
    }


}
