package kg.nurtelecom.opinion.mapper;

import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import kg.nurtelecom.opinion.entity.ArticleComment;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.comment.CommentRequest;
import kg.nurtelecom.opinion.payload.comment.CommentResponse;
import kg.nurtelecom.opinion.payload.comment.ReplyCommentResponse;
import kg.nurtelecom.opinion.payload.user.UserResponse;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-16T20:00:07+0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class ArticleCommentMapperImpl implements ArticleCommentMapper {

    @Override
    public ArticleComment toEntity(CommentRequest commentRequest) {
        if ( commentRequest == null ) {
            return null;
        }

        ArticleComment articleComment = new ArticleComment();

        articleComment.setText( commentRequest.text() );

        return articleComment;
    }

    @Override
    public CommentResponse toModel(ArticleComment articleComment) {
        if ( articleComment == null ) {
            return null;
        }

        Long id = null;
        String text = null;
        LocalDateTime date = null;
        Boolean altered = null;
        UserResponse user = null;
        Integer depth = null;

        id = articleComment.getId();
        text = articleComment.getText();
        date = articleComment.getDate();
        altered = articleComment.isAltered();
        user = userToUserResponse( articleComment.getUser() );
        depth = articleComment.getDepth();

        Integer replies = articleComment.getReplies() != null ? articleComment.getReplies().size() : 0;

        CommentResponse commentResponse = new CommentResponse( id, text, date, altered, user, depth, replies );

        return commentResponse;
    }

    @Override
    public ReplyCommentResponse toReplyModel(ArticleComment articleComment) {
        if ( articleComment == null ) {
            return null;
        }

        Long id = null;
        String text = null;
        LocalDateTime date = null;
        Boolean altered = null;
        UserResponse user = null;
        Integer depth = null;

        id = articleComment.getId();
        text = articleComment.getText();
        date = articleComment.getDate();
        altered = articleComment.isAltered();
        user = userToUserResponse( articleComment.getUser() );
        depth = articleComment.getDepth();

        ReplyCommentResponse replyCommentResponse = new ReplyCommentResponse( id, text, date, altered, user, depth );

        return replyCommentResponse;
    }

    protected UserResponse userToUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        Long id = null;
        String nickname = null;
        String avatar = null;

        id = user.getId();
        nickname = user.getNickname();
        avatar = user.getAvatar();

        UserResponse userResponse = new UserResponse( id, nickname, avatar );

        return userResponse;
    }
}
