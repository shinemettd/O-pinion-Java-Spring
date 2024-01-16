package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.saved_article.SavedArticleResponse;
import kg.nurtelecom.opinion.service.SavedArticlesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-articles")
@CrossOrigin(origins = "*")
@Tag(
        name = "Контроллер для избранных статей",
        description = "В этом контроллере есть возможности добавления, удаления статей из избранных"
)
public class SavedArticleController {
    private final SavedArticlesService savedArticlesService;

    public SavedArticleController(SavedArticlesService savedArticlesService) {
        this.savedArticlesService = savedArticlesService;
    }

    @GetMapping
    @Operation(
            summary = "Получение всех избранных"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<List<SavedArticleResponse>> getSavedArticles(@AuthenticationPrincipal User user) {
        return savedArticlesService.getSavedArticles(user);
    }

    @PostMapping("/{article-id}")
    @Operation(
            summary = "Добавление статьи в избранное по айди"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> addArticleToSaved(@PathVariable("article-id") Long article_id,
                                                  @AuthenticationPrincipal User user) {
        return savedArticlesService.addArticleToSaved(article_id, user);
    }

    @DeleteMapping("/{article-id}")
    @Operation(
            summary = "Удаление статьи из избранных по айди"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> deleteArticleFromSaved(@PathVariable("article-id") Long article_id,
                                                  @AuthenticationPrincipal User user) {
        return savedArticlesService.deleteArticleFromSaved(article_id, user);
    }
}
