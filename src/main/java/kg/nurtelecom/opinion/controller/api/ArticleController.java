package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.article.ArticleGetResponse;
import kg.nurtelecom.opinion.payload.article.ArticleRequest;
import kg.nurtelecom.opinion.payload.article.ArticleResponse;
import kg.nurtelecom.opinion.payload.article.ArticlesGetResponse;
import kg.nurtelecom.opinion.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "*")
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

    @GetMapping
    @Operation(
            summary = "Получение всех статей "
    )
    public ResponseEntity<Page<ArticlesGetResponse>> getArticles(@PageableDefault(page = 0, size = 10, sort = "dateTime") Pageable pageable) {
        return service.getArticles(pageable);
    }

    @GetMapping("/my-articles")
    @Operation(
            summary = "Получение моих статей"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Page<ArticlesGetResponse>> getMyArticles(@PageableDefault(page = 0, size = 10, sort = "dateTime") Pageable pageable,
                                                                   @AuthenticationPrincipal User user) {
        return service.getMyArticles(user, pageable);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Редактирование статьи"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<ArticleResponse> editArticle(@PathVariable("id") Long id,
                                                       @RequestBody @Valid ArticleRequest article) {
        return service.editArticle(article, id);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение статьи по ее id"
    )
    public ResponseEntity<ArticleGetResponse> getArticle(@PathVariable("id") Long id) {
        return service.getArticle(id);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление статьи по ее id"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Long id) {
        return service.deleteArticle(id);
    }

}
