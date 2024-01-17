package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.ArticleComment;
import kg.nurtelecom.opinion.enums.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
    Long countByArticleId(Long articleId);
}