package kg.nurtelecom.opinion.payload.announcement_comment;

import jakarta.validation.constraints.Size;

public record AnnouncementCommentRequest(
        @Size(min = 1, max = 101, message = "Комментарий должен содержать от 1 до 255 символов")
        String text
) {
}
