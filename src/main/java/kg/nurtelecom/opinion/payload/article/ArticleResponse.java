package kg.nurtelecom.opinion.payload.article;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ArticleResponse(
        String title,

        @JsonProperty("short_description")
        String shortDescription,

        String content
) {
}
