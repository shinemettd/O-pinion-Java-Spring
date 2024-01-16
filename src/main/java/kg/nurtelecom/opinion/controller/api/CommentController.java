package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.comment.CommentRequest;
import kg.nurtelecom.opinion.payload.comment.CommentResponse;
import kg.nurtelecom.opinion.service.CommentService;
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

    @PostMapping("/{article-id}")
    public ResponseEntity<CommentResponse> saveComment(
            @PathVariable("article-id") Long articleId,
            @Valid @RequestBody CommentRequest commentRequest,
            @AuthenticationPrincipal User user
    ) {
        return commentService.saveComment(articleId, commentRequest, user);
    }

    @PostMapping("/{id}/reply")
    public ResponseEntity<CommentResponse> replyToComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentRequest commentRequest,
            @AuthenticationPrincipal User user
    ) {
        return commentService.replyToComment(id, commentRequest, user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> updateCommentById(
            @PathVariable Long id, @Valid @RequestBody CommentRequest commentRequest
    ) {
        return commentService.updateCommentById(id, commentRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteCommentById(@PathVariable Long id) {
        commentService.deleteCommentById(id);
    }
}
