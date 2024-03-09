package kg.nurtelecom.opinion.payload.saved_article;

import kg.nurtelecom.opinion.payload.article.ArticleResponse;

public record SavedArticleResponse(
        Long id,
        ArticleResponse article
) {
}
