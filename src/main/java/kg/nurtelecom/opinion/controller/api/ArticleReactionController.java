package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.article_reaction.ArticleReactionRequest;
import kg.nurtelecom.opinion.payload.article_reaction.ArticleReactionResponse;
import kg.nurtelecom.opinion.service.ArticleReactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article-reactions")
@Tag(
        name = "Контроллер для реакций на статьях (лайки и дизлайки)",
        description = "В этом контроллере есть возможности добавления, удаления лайков и дизлайков на статьи"
)
public class ArticleReactionController {
    private final ArticleReactionService articleReactionService;

    public ArticleReactionController(ArticleReactionService articleReactionService) {
        this.articleReactionService = articleReactionService;
    }

    @GetMapping("/likes/{article-id}")
    @Operation(
            summary = "Получить лайки статьи по айди"
    )
    public ResponseEntity<Page<ArticleReactionResponse>> getLikesByArticleId(@PathVariable("article-id") Long articleId,
                                                                             @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return articleReactionService.getLikesByArticleId(articleId, pageable);
    }

    @GetMapping("/dislikes/{article-id}")
    @Operation(
            summary = "Получить дизлайки статьи по айди"
    )
    public ResponseEntity<Page<ArticleReactionResponse>> getDislikesByArticleId(@PathVariable("article-id") Long articleId,
                                                                             @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return articleReactionService.getDislikesByArticleId(articleId, pageable);
    }

    @PostMapping
    @Operation(
            summary = "Добавление реакции на статью"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<String> addReactionToArticle(@RequestBody ArticleReactionRequest reaction,
                                                  @AuthenticationPrincipal User user) {
        return articleReactionService.addReactionToArticle(reaction, user);
    }

}
