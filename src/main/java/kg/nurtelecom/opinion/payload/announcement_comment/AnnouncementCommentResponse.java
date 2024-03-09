package kg.nurtelecom.opinion.payload.announcement_comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import kg.nurtelecom.opinion.payload.user.UserResponse;

import java.time.LocalDateTime;

public record AnnouncementCommentResponse(
        Long id,
        String text,
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        LocalDateTime date,
        Boolean edited,
        UserResponse user
) {
}
