package kg.nurtelecom.opinion.payload.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ArticleCreateResponse(
        String title,

        @JsonProperty("short_description")
        String shortDescription,

        String content
) {
}
