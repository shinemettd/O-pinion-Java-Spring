package kg.nurtelecom.opinion.payload.article;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ArticleResponse(
        Long id,

        String title,

        @JsonProperty("short_description")
        String shortDescription,

        @JsonProperty("cover_image")
        String coverImage

) {
}
