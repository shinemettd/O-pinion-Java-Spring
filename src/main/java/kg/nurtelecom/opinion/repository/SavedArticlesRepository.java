package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.SavedArticle;
import kg.nurtelecom.opinion.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavedArticlesRepository extends JpaRepository<SavedArticle, Long> {
    Optional<SavedArticle> findByArticleIdAndUserId(Long articleId, Long id);

    List<SavedArticle> findAllByUser(User user);
}
