package kg.nurtelecom.opinion.payload.article_reaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import kg.nurtelecom.opinion.enums.ReactionType;
import kg.nurtelecom.opinion.payload.user.GetUserResponse;

public record ArticleReactionResponse(
        @JsonProperty("reaction_type")
        ReactionType reactionType,
        GetUserResponse user
) {
}
