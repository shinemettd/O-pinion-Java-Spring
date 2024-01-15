package kg.nurtelecom.opinion.mapper;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.payload.article.ArticleGetResponse;
import kg.nurtelecom.opinion.payload.article.ArticleRequest;
import kg.nurtelecom.opinion.payload.article.ArticleResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    Article toEntity(ArticleRequest article);

    ArticleResponse toModel(Article article);

    ArticleGetResponse toArticleGetResponse(Article article);

    List<ArticleGetResponse> toArticleGetResponseList(List<Article> articles);

}