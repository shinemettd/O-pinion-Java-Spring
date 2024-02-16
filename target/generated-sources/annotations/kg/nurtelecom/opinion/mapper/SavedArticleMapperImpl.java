package kg.nurtelecom.opinion.mapper;

import javax.annotation.processing.Generated;
import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.SavedArticle;
import kg.nurtelecom.opinion.payload.article.ArticleResponse;
import kg.nurtelecom.opinion.payload.saved_article.SavedArticleResponse;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-16T20:00:07+0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class SavedArticleMapperImpl implements SavedArticleMapper {

    @Override
    public SavedArticleResponse toSavedArticleResponse(SavedArticle savedArticle) {
        if ( savedArticle == null ) {
            return null;
        }

        Long id = null;
        ArticleResponse article = null;

        id = savedArticle.getId();
        article = articleToArticleResponse( savedArticle.getArticle() );

        SavedArticleResponse savedArticleResponse = new SavedArticleResponse( id, article );

        return savedArticleResponse;
    }

    protected ArticleResponse articleToArticleResponse(Article article) {
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
