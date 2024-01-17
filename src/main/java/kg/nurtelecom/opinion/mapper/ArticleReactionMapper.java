package kg.nurtelecom.opinion.mapper;

import kg.nurtelecom.opinion.entity.ArticleReaction;
import kg.nurtelecom.opinion.payload.article_reaction.ArticleReactionResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ArticleReactionMapper {
    ArticleReactionResponse toArticleReactionResponse(ArticleReaction articleReaction);

    default Page<ArticleReactionResponse> toArticleReactionResponsePage(Page<ArticleReaction> articleReactions) {
        return articleReactions.map(this::toArticleReactionResponse);
    }
}
