package kg.nurtelecom.opinion.payload.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.ArticleStatus;

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
