package kg.nurtelecom.opinion.payload.article;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ArticleCreateResponse(
        String title,

        String shortDescription,

        String content
) {
}
