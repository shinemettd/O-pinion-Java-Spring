package kg.nurtelecom.opinion.payload.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import kg.nurtelecom.opinion.payload.image.ImageResponse;

import java.util.List;

public record ArticleResponse(
        Long id,

        String title,

        @JsonProperty("short_description")
        String shortDescription,

        String content,

        @JsonProperty("cover_image")
        String coverImage,

        @JsonProperty("content_images")
        List<ImageResponse> contentImages
) {
}
