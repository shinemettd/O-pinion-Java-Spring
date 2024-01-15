package kg.nurtelecom.opinion.mapper;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.payload.article.ArticleCreateRequest;
import kg.nurtelecom.opinion.payload.article.ArticleCreateResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleCreateMapper {

    Article toEntity(ArticleCreateRequest article);

    ArticleCreateResponse toModel(Article article);

}

