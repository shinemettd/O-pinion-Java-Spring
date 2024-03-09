package kg.nurtelecom.opinion.payload.article_comment;

import jakarta.validation.constraints.Size;

public record ArticleCommentRequest(
        @Size(min = 5, max = 255, message = "Комментарий должен содержать от 5 до 255 символов")
        String text
) {
}
