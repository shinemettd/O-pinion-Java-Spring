package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
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
            summary = "Сохранение картинки статьи (в том числе и главной ) на сервер  "
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<String> loadImage(@RequestPart("photo") MultipartFile photo) {
        return new ResponseEntity<>(imageService.loadArticleImage(photo), HttpStatus.CREATED);
    }

    @DeleteMapping
    @Operation(
            summary = "Удаление картинки с контента статьи  с сервера "
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> deleteImage(@RequestParam("image-path") String imagePath) {
        return imageService.deleteImage(imagePath);
    }


    @PutMapping("/{article-id}")
    @Operation(
            summary = "Изменение главного фото для статьи с таким id"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<String> updateCoverImage(@PathVariable("article-id") Long articleId,
                                                 @RequestPart("photo") MultipartFile image,
                                                 @AuthenticationPrincipal User user) {
        return imageService.updateCoverImage(articleId, image, user);
    }

    @DeleteMapping("/{article-id}")
    @Operation(
            summary = "Удаление главного фото статьи"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> deleteCoverImage(@PathVariable("article-id") Long articleId,
                                                 @AuthenticationPrincipal User user) {
        return imageService.deleteCoverImage(articleId, user);
    }

    @PutMapping("/change-avatar")
    @Operation(
            summary = "Сменить аватарку в профиле пользователя"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> changeUserAvatar(@AuthenticationPrincipal User user,
                                                 @RequestPart("photo") MultipartFile photo) {
        return imageService.changeUserAvatar(user, photo);
    }


}
