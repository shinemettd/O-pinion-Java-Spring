package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.saved_article.SavedArticleResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SavedArticlesService {

    ResponseEntity<List<SavedArticleResponse>> getSavedArticles(User user);

    ResponseEntity<Void> addArticleToSaved(Long articleId, User user);

    ResponseEntity<Void> deleteArticleFromSaved(Long articleId, User user);

}
