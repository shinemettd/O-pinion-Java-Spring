package kg.nurtelecom.opinion.mapper;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.payload.article.ArticleGetResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleGetMapper {
    ArticleGetResponse toModel(Article article);
}