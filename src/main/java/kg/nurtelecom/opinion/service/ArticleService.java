package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.article.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface ArticleService {
    ResponseEntity<ArticleResponse> createArticle(ArticleRequest article, User user);
    ResponseEntity<ArticleResponse> createArticleDraft(ArticleDraftRequest article, User user);

    ResponseEntity<Void> undraftArticle(Long articleId,  User user);
    ArticleResponse editArticle(ArticleDraftRequest editedArticle, Long id, User user);
    ResponseEntity<Void> updateArticleInDBFromCache(Long articleId, User user);

    ArticleGetDTO getArticle(Long id, User user);

    ResponseEntity<Long> getArticleRating(Long id, User user);

    ResponseEntity<Long> getArticleTotalFavourites(Long id);

    ResponseEntity<Void> deleteArticle(Long id, User user);

    ResponseEntity<Page<MyArticlesGetDTO>> getMyArticles(User user, Pageable pageable);

    ResponseEntity<Page<ArticlesGetDTO>> getUserArticles(Long userId, Pageable pageable);

    ResponseEntity<Page<ArticlesGetDTO>> getArticles(Pageable pageable, User user);

    ResponseEntity<String> shareArticle(Long articleId, String shareType);

    ResponseEntity<Void> shareArticleByEmail(Long articleId, String to , String from);

    ResponseEntity<Page<ArticlesGetDTO>> searchArticle(Pageable pageable, String searchQuery, User user);

}
