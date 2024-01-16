package kg.nurtelecom.opinion.entity;

import jakarta.persistence.*;
import kg.nurtelecom.opinion.enums.CommentStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "article_comments")
public class ArticleComment extends BaseEntity {
    private String text;
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    private CommentStatus status;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;
    @ManyToOne
    @JoinColumn(name = "parent_comment")
    private ArticleComment parentComment;

    public ArticleComment() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public CommentStatus getStatus() {
        return status;
    }

    public void setStatus(CommentStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public ArticleComment getParentComment() {
        return parentComment;
    }

    public void setParentComment(ArticleComment parentComment) {
        this.parentComment = parentComment;
    }
}
