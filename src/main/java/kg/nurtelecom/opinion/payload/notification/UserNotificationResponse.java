package kg.nurtelecom.opinion.payload.notification;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record UserNotificationResponse(
        String title,
        String content,
        String url,
        @JsonProperty("date_time")
        LocalDateTime dateTime,
        @JsonProperty("is_read")
        Boolean read
) {
}
