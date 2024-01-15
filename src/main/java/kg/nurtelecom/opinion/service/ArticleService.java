package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.article.ArticleCreateRequest;
import kg.nurtelecom.opinion.payload.article.ArticleCreateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface ArticleService {
    ResponseEntity<ArticleCreateResponse> createArticle(ArticleCreateRequest article, User user);
}
