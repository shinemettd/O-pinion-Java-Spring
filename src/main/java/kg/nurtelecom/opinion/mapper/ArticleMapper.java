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

    Article toEntityFromDraftRequest(ArticleDraftRequest articleDraft);
    
}