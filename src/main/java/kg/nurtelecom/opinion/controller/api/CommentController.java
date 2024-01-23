package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.comment.*;
import kg.nurtelecom.opinion.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article-comments")
@CrossOrigin(origins = "*")
@Tag(
        name = "Контроллер для управления комментариями статей",
        description = "В этом контроллере описаны запросы на CRUD методы, а также запрос на создание ответа к комментарию"
)
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{article-id}")
    public ResponseEntity<Page<NestedCommentResponse>> getAllComments(
            @PathVariable("article-id") Long articleId,
            @PageableDefault Pageable pageable
    ) {
        return commentService.getAllComments(articleId, pageable);
    }

    @PostMapping("/{article-id}")
    public ResponseEntity<CommentResponse> saveComment(
            @PathVariable("article-id") Long articleId,
            @Valid @RequestBody CommentRequest commentRequest,
            @AuthenticationPrincipal User user
    ) {
        return commentService.saveComment(articleId, commentRequest, user);
    }

    @PostMapping("/{id}/replies")
    public ResponseEntity<CommentResponse> replyToComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentRequest commentRequest,
            @AuthenticationPrincipal User user
    ) {
        return commentService.replyToComment(id, commentRequest, user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> updateCommentById(
            @PathVariable Long id,
            @Valid @RequestBody CommentRequest commentRequest,
            @AuthenticationPrincipal User user
    ) {
        return commentService.updateCommentById(id, commentRequest, user);
    }

    @DeleteMapping("/{id}")
    public void deleteCommentById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        commentService.deleteCommentById(id, user);
    }
}
