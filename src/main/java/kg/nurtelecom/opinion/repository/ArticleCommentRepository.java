package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.ArticleComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {

    @Query("select c from ArticleComment c where c.article.id = :articleId and " +
            "c.parentComment is null order by c.date desc")
    Page<ArticleComment> findRootComments(@Param("articleId") Long articleId, Pageable pageable);

    @Query("select c from ArticleComment c where c.parentComment.id = :parentCommentId")
    Page<ArticleComment> findCommentReplies(@Param("parentCommentId") Long parentCommentId, Pageable pageable);

    Long countByArticleId(Long articleId);
}
