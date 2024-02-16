package kg.nurtelecom.opinion.mapper;

import java.util.Date;
import javax.annotation.processing.Generated;
import kg.nurtelecom.opinion.entity.ArticleReaction;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.ReactionType;
import kg.nurtelecom.opinion.payload.article_reaction.ArticleReactionResponse;
import kg.nurtelecom.opinion.payload.user.GetUserResponse;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-16T20:00:07+0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class ArticleReactionMapperImpl implements ArticleReactionMapper {

    @Override
    public ArticleReactionResponse toArticleReactionResponse(ArticleReaction articleReaction) {
        if ( articleReaction == null ) {
            return null;
        }

        ReactionType reactionType = null;
        GetUserResponse user = null;

        reactionType = articleReaction.getReactionType();
        user = userToGetUserResponse( articleReaction.getUser() );

        ArticleReactionResponse articleReactionResponse = new ArticleReactionResponse( reactionType, user );

        return articleReactionResponse;
    }

    protected GetUserResponse userToGetUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        Long id = null;
        String firstName = null;
        String lastName = null;
        String nickname = null;
        String email = null;
        String avatar = null;
        Date birthDate = null;

        id = user.getId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        nickname = user.getNickname();
        email = user.getEmail();
        avatar = user.getAvatar();
        birthDate = user.getBirthDate();

        GetUserResponse getUserResponse = new GetUserResponse( id, firstName, lastName, nickname, email, avatar, birthDate );

        return getUserResponse;
    }
}
