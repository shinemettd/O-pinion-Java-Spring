package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.article.ArticleGetDTO;
import kg.nurtelecom.opinion.payload.article.ArticleRequest;
import kg.nurtelecom.opinion.payload.article.ArticleResponse;
import kg.nurtelecom.opinion.payload.article.ArticlesGetDTO;
import kg.nurtelecom.opinion.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/articles")
@Tag(
        name = "Контроллер для всех CRUD операций над статьями"
)
public class ArticleController {

    private final ArticleService service;

    @Autowired
    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(
            summary = "Создание статьи "
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody @Valid ArticleRequest article,
                                                         @AuthenticationPrincipal User user) {
        return service.createArticle(article, user);
    }

    @PostMapping("/{id}/set-content")
    @Operation(
            summary = "После создания статьи  устанавливаем ей контент в виде html файла "
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<ArticleResponse> setArticleContent(@PathVariable("id") Long articleId,
                                                             @RequestPart("article-content") MultipartFile content,
                                                             @AuthenticationPrincipal User user) {
        return service.setContent(articleId, content, user);
    }

    @PutMapping("/{id}/change-content")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<ArticleResponse> changeArticleContent(@PathVariable("id") Long articleId,
                                                                @RequestPart("article-content") MultipartFile content,
                                                                @AuthenticationPrincipal User user) {
        return service.setContent(articleId, content, user);
    }

    @GetMapping
    @Operation(
            summary = "Получение всех статей  "
    )
    public ResponseEntity<Page<ArticlesGetDTO>> getArticles(@PageableDefault(page = 0, size = 10, sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable,
                                                            @AuthenticationPrincipal User user) {
        return service.getArticles(pageable, user);

    }

    @GetMapping("/my-articles")
    @Operation(
            summary = "Получение моих статей"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Page<ArticlesGetDTO>> getMyArticles(@PageableDefault(page = 0, size = 10, sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable,
                                                              @AuthenticationPrincipal User user) {

        if(user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return service.getMyArticles(user, pageable);
    }

    @GetMapping("/{userId}/articles")
    @Operation(
            summary = "Получение  статей пользователя"
    )
    public ResponseEntity<Page<ArticlesGetDTO>> getUserArticles(@PathVariable("userId") Long userId,
                                                                @PageableDefault(page = 0, size = 10, sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable) {

        return service.getUserArticles(userId, pageable);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Редактирование статьи"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<ArticleResponse> editArticle(@PathVariable("id") Long id,
                                                       @RequestBody @Valid ArticleRequest article,
                                                       @AuthenticationPrincipal User user) {
        return service.editArticle(article, id, user);
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Получение статьи по ее id"
    )
    public ResponseEntity<ArticleGetDTO> getArticle(@PathVariable("id") Long id,
                                                    @AuthenticationPrincipal User user) {

        return service.getArticle(id, user);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление статьи по ее id"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Long id,
                                              @AuthenticationPrincipal User user) {
        return service.deleteArticle(id, user);
    }


    @GetMapping("/{id}/share")
    @Operation(
            summary = "Поделиться статьей"
    )
    public ResponseEntity<String> shareArticle(@PathVariable("id") Long articleId,
                                               @RequestParam(value = "share-type", defaultValue = "article") String shareType
                                               ) {
        return service.shareArticle(articleId, shareType);
    }

    @GetMapping("/{id}/share/email")
    @Operation(
            summary = "Поделиться статьей по почте "
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> shareArticleByEmail(@PathVariable("id") Long articleId,
                                                    @RequestParam(value = "to", required = true) @NotBlank @Email String to,
                                                    @AuthenticationPrincipal User user) {
        if(user != null) {
            return service.shareArticleByEmail(articleId, to, user.getEmail());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
