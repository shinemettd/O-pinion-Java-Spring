package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.ArticleComment;
import kg.nurtelecom.opinion.payload.comment.CommentRepliesView;
import kg.nurtelecom.opinion.payload.comment.CommentView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {

    @Query("""
       select
            c.id as id,
            c.text as text,
            c.date as date,
            c.altered as altered,
            c.user.id as userId,
            c.user.nickname as userNickname,
            c.user.avatar as userAvatar,
            case when size(c.replies) > 0 then true else false end as hasReplies
       from ArticleComment c
       where c.article.id = :articleId and c.parentComment is null
       order by c.date desc""")
    Page<CommentView> findRootComments(@Param("articleId") Long articleId, Pageable pageable);

    @Query("""
       select
            c.id as id,
            c.text as text,
            c.date as date,
            c.altered as altered,
            c.user.id as userId,
            c.user.nickname as userNickname,
            c.user.avatar as userAvatar,
            c.parentComment.id as parentId,
            case when size(c.replies) > 0 then true else false end as hasReplies
       from ArticleComment c
       where c.parentComment.id = :parentCommentId""")
    Page<CommentRepliesView> findCommentReplies(Long parentCommentId, Pageable pageable);

    Long countByArticleId(Long articleId);
}
