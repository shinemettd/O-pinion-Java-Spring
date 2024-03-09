package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.SavedArticle;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.ReactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SavedArticlesRepository extends JpaRepository<SavedArticle, Long> {
    Optional<SavedArticle> findByArticleIdAndUserId(Long articleId, Long id);

    Page<SavedArticle> findAllByUser(User user, Pageable pageable);

    Long countByArticleId(Long articleId);

    boolean existsByArticleIdAndUserId(Long articleId, Long id);

    void deleteByArticleIdAndUserId(Long articleId, Long id);
}
