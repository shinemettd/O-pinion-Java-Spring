package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.nurtelecom.opinion.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "*")
@Tag(
        name = "Контроллер для добавления и удаления фотографий "
)
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    @Operation(
            summary = "Сохранение картинки для статьи с id "
    )
    public ResponseEntity<String> loadImage(@RequestPart("photo") MultipartFile photo) {
        return new ResponseEntity<>(imageService.loadImage(photo), HttpStatus.CREATED);
    }
}
