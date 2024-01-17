package kg.nurtelecom.opinion.payload.article_reaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import kg.nurtelecom.opinion.enums.ReactionType;

public record ArticleReactionRequest(
        @JsonProperty("article_id")
        Long articleId,

        @JsonProperty("reaction_type")
        ReactionType reactionType
) {
}
