package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.ArticleReaction;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.ReactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleReactionRepository extends JpaRepository<ArticleReaction, Long> {
    Optional<ArticleReaction> findByArticleAndUser(Article article, User user);

    boolean existsByArticleAndUser(Article article, User user);

    Page<ArticleReaction> findAllByArticleIdAndReactionType(Long articleId, ReactionType reactionType, Pageable pageable);

    Long countByArticleIdAndReactionType(Long articleId, ReactionType reactionType);
}
