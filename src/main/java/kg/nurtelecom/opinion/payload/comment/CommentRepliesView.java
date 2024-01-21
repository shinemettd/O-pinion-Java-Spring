package kg.nurtelecom.opinion.payload.comment;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface CommentRepliesView {
    Long getId();
    String getText();
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime getDate();
    Boolean getAltered();
    Long getUserId();
    String getUserNickname();
    String getUserAvatar();
    Long getParentId();
    Boolean getHasReplies();
}
