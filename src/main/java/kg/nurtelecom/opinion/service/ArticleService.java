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


    ResponseEntity<ArticleResponse> editArticle(ArticleRequest editedArticle, Long id, User user);

    ResponseEntity<ArticleGetDTO> getArticle(Long id, User user);

    ResponseEntity<Long> getArticleRating(Long id, User user);

    ResponseEntity<Void> deleteArticle(Long id, User user);

    ResponseEntity<Page<ArticlesGetDTO>> getMyArticles(User user, Pageable pageable);

    ResponseEntity<Page<ArticlesGetDTO>> getUserArticles(Long userId, Pageable pageable);

    ResponseEntity<Page<ArticlesGetDTO>> getArticles(Pageable pageable, User user);

    ResponseEntity<String> shareArticle(Long articleId, String shareType);

    ResponseEntity<Void> shareArticleByEmail(Long articleId, String to , String from);

    ResponseEntity<Page<ArticlesGetDTO>> searchArticle(Pageable pageable, String searchQuery, User user);

}
