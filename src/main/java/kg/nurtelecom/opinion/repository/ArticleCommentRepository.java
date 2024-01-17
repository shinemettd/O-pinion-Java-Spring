package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.ArticleComment;
import kg.nurtelecom.opinion.enums.CommentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
    Page<ArticleComment> findByArticle_IdAndStatusNotAndParentCommentIsNull(
            Long articleId, CommentStatus status, Pageable pageable
    );
}
