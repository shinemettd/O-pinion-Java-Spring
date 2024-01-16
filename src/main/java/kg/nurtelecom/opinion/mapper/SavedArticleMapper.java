package kg.nurtelecom.opinion.mapper;

import kg.nurtelecom.opinion.entity.SavedArticle;
import kg.nurtelecom.opinion.payload.saved_article.SavedArticleResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SavedArticleMapper {
    List<SavedArticleResponse> toSavedArticleResponseList(List<SavedArticle> savedArticles);
}
