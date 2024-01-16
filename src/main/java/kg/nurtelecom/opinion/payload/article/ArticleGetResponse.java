package kg.nurtelecom.opinion.payload.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.ArticleStatus;
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

        @JsonProperty("views_count")
        int viewsCount

) {
}
