package kg.nurtelecom.opinion.entity;

import jakarta.persistence.*;
import kg.nurtelecom.opinion.enums.ReactionType;

@Entity
@Table(name = "article_reaction")
public class ArticleReaction extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;

    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public ArticleReaction() {
    }

    public ArticleReaction(ReactionType reactionType, Article article, User user) {
        this.reactionType = reactionType;
        this.article = article;
        this.user = user;
    }

    public ReactionType getReactionType() {
        return reactionType;
    }

    public void setReactionType(ReactionType reactionType) {
        this.reactionType = reactionType;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
