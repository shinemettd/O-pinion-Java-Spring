package kg.nurtelecom.opinion.mapper;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.SavedArticle;
import kg.nurtelecom.opinion.payload.saved_article.SavedArticleResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface SavedArticleMapper {
    SavedArticleResponse toSavedArticleResponse(SavedArticle savedArticle);

    default Page<SavedArticleResponse> toSavedArticleResponsePage(Page<SavedArticle> savedArticles) {
        return savedArticles.map(this::toSavedArticleResponse);
    }
}
