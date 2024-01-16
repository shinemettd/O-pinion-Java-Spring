package kg.nurtelecom.opinion.payload.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import kg.nurtelecom.opinion.payload.user.UserResponse;


import java.time.LocalDateTime;

public record ArticleGetResponse(
        String title,

        @JsonProperty("short_description")
        String shortDescription,

        String content,
        UserResponse author,

        @JsonProperty("date_time")
        LocalDateTime dateTime,
git status
        @JsonProperty("views_count")
        Integer viewsCount

) {
}
