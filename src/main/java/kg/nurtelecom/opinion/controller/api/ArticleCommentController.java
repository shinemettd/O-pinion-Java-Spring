package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.article_comment.ArticleCommentRequest;
import kg.nurtelecom.opinion.payload.article_comment.ArticleCommentResponse;
import kg.nurtelecom.opinion.payload.article_comment.ArticleNestedCommentResponse;
import kg.nurtelecom.opinion.service.ArticleCommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article-comments")
@Tag(
        name = "Контроллер для управления комментариями статей",
        description = "В этом контроллере описаны запросы на CRUD методы, а также запрос на создание ответа к комментарию"
)
public class ArticleCommentController {

    private final ArticleCommentService commentService;

    public ArticleCommentController(ArticleCommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{article-id}/total-comments")
    @Operation(
            summary = "Возвращает количество комментариев статьи по ее id"
    )
    public ResponseEntity<Long> getTotalComments(@PathVariable("article-id") Long articleId) {
        return commentService.getTotalComments(articleId);
    }

    @GetMapping("/{article-id}")
    public ResponseEntity<Page<ArticleNestedCommentResponse>> getAllComments(
            @PathVariable("article-id") Long articleId,
            @PageableDefault(sort = "date", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return commentService.getAllComments(articleId, pageable);
    }

    @PostMapping("/{article-id}")
    public ResponseEntity<ArticleCommentResponse> saveComment(
            @PathVariable("article-id") Long articleId,
            @Valid @RequestBody ArticleCommentRequest articleCommentRequest,
            @AuthenticationPrincipal User user
    ) {
        return commentService.saveComment(articleId, articleCommentRequest, user);
    }

    @PostMapping("/{id}/replies")
    public ResponseEntity<ArticleCommentResponse> replyToComment(
            @PathVariable Long id,
            @Valid @RequestBody ArticleCommentRequest articleCommentRequest,
            @AuthenticationPrincipal User user
    ) {
        return commentService.replyToComment(id, articleCommentRequest, user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleCommentResponse> updateCommentById(
            @PathVariable Long id,
            @Valid @RequestBody ArticleCommentRequest articleCommentRequest,
            @AuthenticationPrincipal User user
    ) {
        return commentService.updateCommentById(id, articleCommentRequest, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return commentService.deleteCommentById(id, user);
    }
}
