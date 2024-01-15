package kg.nurtelecom.opinion.payload.article;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record ArticleGetResponse(
        String title,

        @JsonProperty("short_description")
        String shortDescription,

        String content,

        @JsonProperty("date_time")
        LocalDateTime dateTime,

        @JsonProperty("views_count")
        int viewsCount

) {
}
