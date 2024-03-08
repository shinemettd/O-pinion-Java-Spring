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
import kg.nurtelecom.opinion.service.DailyVisitService;
import kg.nurtelecom.opinion.service.implementations.DailyVisitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
@Tag(
        name = "Контроллер для всех CRUD операций над статьями"
)
public class ArticleController {

    private final ArticleService service;
    private final DailyVisitService dailyVisitService;

    @Autowired
    public ArticleController(ArticleService service, DailyVisitServiceImpl dailyVisitService) {
        this.service = service;
        this.dailyVisitService = dailyVisitService;
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

    @PostMapping("/drafts")
    @Operation(
            summary = "Создание черновика статьи "
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<ArticleResponse> createArticleDraft(@RequestBody @Valid ArticleRequest article,
                                                         @AuthenticationPrincipal User user) {
        return service.createArticleDraft(article, user);
    }

    @PutMapping("/{id}/undraft")
    @Operation(
            summary = "Отправить статью из черновиков на модерацию "
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> undraftArticle(@PathVariable("id") Long id,
                                                       @AuthenticationPrincipal User user) {
        return service.undraftArticle(id, user);
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

    @GetMapping
    @Operation(
            summary = "Получение всех статей  "
    )
    public ResponseEntity<Page<ArticlesGetDTO>> getArticles(@PageableDefault(page = 0, size = 10, sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable,
                                                            @AuthenticationPrincipal User user) {
        dailyVisitService.incrementDailyVisitCount();
        return service.getArticles(pageable, user);
    }

    @GetMapping("/search")
    @Operation(
            summary = "Поиск статей по title и по тегам в статьях "
    )
    public ResponseEntity<Page<ArticlesGetDTO>> searchArticle(@PageableDefault(page = 0, size = 10, sort = "dateTime") Pageable pageable,
                                                              @RequestParam("query") String searchQuery,
                                                              @AuthenticationPrincipal User user) {
        return service.searchArticle(pageable, searchQuery, user);
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



    @GetMapping("/{id}")
    @Operation(
            summary = "Получение статьи по ее id"
    )
    public ResponseEntity<ArticleGetDTO> getArticle(@PathVariable("id") Long id,
                                                    @AuthenticationPrincipal User user) {

        return service.getArticle(id, user);
    }

    @GetMapping("/{id}/rating")
    @Operation(
        summary = "Получение рейтинга статьи по ее id"
    )
    public ResponseEntity<Long> getArticleRating(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
        return service.getArticleRating(id, user);
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
            return service.shareArticleByEmail(articleId, to, user.getNickname());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
