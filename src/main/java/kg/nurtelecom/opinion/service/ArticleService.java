package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.article.ArticleRequest;
import kg.nurtelecom.opinion.payload.article.ArticleResponse;
import kg.nurtelecom.opinion.payload.article.ArticleGetResponse;
import kg.nurtelecom.opinion.payload.article.ArticlesGetResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ArticleService {
    ResponseEntity<ArticleResponse> createArticle(ArticleRequest article, User user);
    ResponseEntity<ArticleResponse> editArticle(ArticleRequest editedArticle, Long id);
    ResponseEntity<ArticleGetResponse> getArticle(Long id);

    ResponseEntity<Void> deleteArticle(Long id);

    ResponseEntity<Page<ArticlesGetResponse>> getMyArticles(User user, Pageable pageable);
    ResponseEntity<Page<ArticlesGetResponse>> getArticles(Pageable pageable);
}
