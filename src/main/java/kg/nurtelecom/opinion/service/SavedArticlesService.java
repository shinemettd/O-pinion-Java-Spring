package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.saved_article.SavedArticleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;


public interface SavedArticlesService {

    ResponseEntity<Page<SavedArticleResponse>> getSavedArticles(User user, Pageable pageable);

    ResponseEntity<Void> addArticleToSaved(Long articleId, User user);

    ResponseEntity<Void> deleteArticleFromSaved(Long articleId, User user);

}
