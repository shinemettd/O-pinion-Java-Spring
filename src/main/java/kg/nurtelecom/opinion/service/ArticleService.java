package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.article.ArticleRequest;
import kg.nurtelecom.opinion.payload.article.ArticleResponse;
import kg.nurtelecom.opinion.payload.article.ArticleGetResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ArticleService {
    ResponseEntity<ArticleResponse> createArticle(ArticleRequest article, User user);
    ResponseEntity<ArticleResponse> editArticle(ArticleRequest editedArticle, long id);
    ResponseEntity<List<ArticleGetResponse>> getArticles();
}
