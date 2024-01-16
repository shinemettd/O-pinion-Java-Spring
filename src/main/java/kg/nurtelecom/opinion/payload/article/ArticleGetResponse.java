package kg.nurtelecom.opinion.payload.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import kg.nurtelecom.opinion.entity.Image;
import kg.nurtelecom.opinion.payload.image.ImageResponse;
import kg.nurtelecom.opinion.payload.user.UserResponse;

import java.time.LocalDateTime;
import java.util.List;

public record ArticleGetResponse(
        Long id,

        String title,

        @JsonProperty("short_description")
        String shortDescription,

        String content,

        UserResponse author,

        @JsonProperty("date_time")
        LocalDateTime dateTime,

        @JsonProperty("views_count")
        Integer viewsCount,

        @JsonProperty("content_images")
        List<ImageResponse> contentImages,

        @JsonProperty("cover_image")
        String coverImage
) {
}
