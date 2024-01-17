package kg.nurtelecom.opinion.payload.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import kg.nurtelecom.opinion.enums.CommentStatus;
import kg.nurtelecom.opinion.payload.user.UserResponse;

import java.time.LocalDateTime;
import java.util.List;

public record NestedCommentResponse(
        Long id,
        String text,
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        LocalDateTime date,
        CommentStatus status,
        UserResponse user,
        List<NestedCommentResponse> replies
) {
}
