package kg.nurtelecom.opinion.mapper;

import javax.annotation.processing.Generated;
import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.payload.article.ArticleRequest;
import kg.nurtelecom.opinion.payload.article.ArticleResponse;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-16T20:00:07+0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class ArticleMapperImpl implements ArticleMapper {

    @Override
    public Article toEntity(ArticleRequest article) {
        if ( article == null ) {
            return null;
        }

        Article article1 = new Article();

        article1.setTitle( article.title() );
        article1.setShortDescription( article.shortDescription() );
        article1.setContent( article.content() );
        article1.setCoverImage( article.coverImage() );

        return article1;
    }

    @Override
    public ArticleResponse toModel(Article article) {
        if ( article == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        String shortDescription = null;
        String coverImage = null;

        id = article.getId();
        title = article.getTitle();
        shortDescription = article.getShortDescription();
        coverImage = article.getCoverImage();

        ArticleResponse articleResponse = new ArticleResponse( id, title, shortDescription, coverImage );

        return articleResponse;
    }
}
