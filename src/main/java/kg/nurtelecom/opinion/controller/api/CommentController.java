package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.comment.CommentRequest;
import kg.nurtelecom.opinion.payload.comment.CommentResponse;
import kg.nurtelecom.opinion.payload.comment.ReplyCommentResponse;
import kg.nurtelecom.opinion.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{article-id}")
    public ResponseEntity<Page<CommentResponse>> getRootComments(
            @PathVariable("article-id") Long articleId,
            @PageableDefault Pageable pageable
    ) {
        return commentService.getRootComments(articleId, pageable);
    }

    @GetMapping("/{id}/replies")
    public ResponseEntity<Page<ReplyCommentResponse>> getCommentReplies(
            @PathVariable Long id,
            @PageableDefault Pageable pageable
    ) {
        return commentService.getCommentReplies(id, pageable);
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
    public ResponseEntity<Void> deleteCommentById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return commentService.deleteCommentById(id, user);
    }
}
