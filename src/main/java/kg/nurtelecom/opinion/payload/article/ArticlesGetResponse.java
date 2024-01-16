package kg.nurtelecom.opinion.payload.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import kg.nurtelecom.opinion.payload.image.ImageResponse;
import kg.nurtelecom.opinion.payload.user.UserResponse;

import java.time.LocalDateTime;

public record ArticlesGetResponse(
        Long id,
        String title,

        @JsonProperty("short_description")
        String shortDescription,

        UserResponse author,

        @JsonProperty("date_time")
        LocalDateTime dateTime,

        @JsonProperty("views_count")
        Integer viewsCount,

        @JsonProperty("cover_image")
        ImageResponse coverImage
) {

}
