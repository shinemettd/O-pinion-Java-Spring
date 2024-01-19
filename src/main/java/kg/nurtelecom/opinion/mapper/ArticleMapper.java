package kg.nurtelecom.opinion.mapper;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.payload.article.*;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    Article toEntity(ArticleRequest article);

    ArticleResponse toModel(Article article);

    ArticleGetDTO toArticleGetDTO(Article article);

    default Page<ArticlesGetDTO> toArticlesGetDTOPage(Page<Article> articles) {
        return articles.map(article -> toArticlesGetDTO(article));
    }

    ArticlesGetDTO toArticlesGetDTO(Article article);

}