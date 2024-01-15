package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.article.ArticleCreateResponse;
import kg.nurtelecom.opinion.payload.article.ArticleCreateRequest;
import kg.nurtelecom.opinion.payload.article.ArticleGetResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ArticleService {
    ResponseEntity<ArticleCreateResponse> createArticle(ArticleCreateRequest article, User user);
    ResponseEntity<List<ArticleGetResponse>> getArticles();
}
