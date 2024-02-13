package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.Post;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Post, Long> {

    Page<Article> findByAuthor(User user, Pageable pageable);
    Page<Article> findByAuthorAndStatus(User user, Pageable pageable, ArticleStatus status);
    Page<Article> findByStatus(@Param("status") ArticleStatus status, Pageable pageable);

    @Modifying
    @Query("UPDATE Article a SET a.viewsCount = a.viewsCount + 1 WHERE a.id = :articleId")
    void incrementViewsCount(@Param("articleId") Long articleId);

    Optional<Article> findByIdAndStatusNotIn(Long id, List<ArticleStatus> excludedStatuses);
}
