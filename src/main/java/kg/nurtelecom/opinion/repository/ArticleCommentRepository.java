package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.ArticleComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {

    Page<ArticleComment> findByArticle_IdAndParentCommentIsNull(
            Long articleId, Pageable pageable
    );

    Long countByArticleId(Long articleId);
}
