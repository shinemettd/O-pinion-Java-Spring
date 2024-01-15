package kg.nurtelecom.opinion.payload.article;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ArticleCreateResponse(
        String title,

        @JsonProperty("short_description")
        String shortDescription,

        String content
) {
}
