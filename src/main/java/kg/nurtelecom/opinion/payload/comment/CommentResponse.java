package kg.nurtelecom.opinion.payload.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import kg.nurtelecom.opinion.payload.user.UserResponse;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String text,
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        LocalDateTime date,
        UserResponse user
) {
}