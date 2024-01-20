package kg.nurtelecom.opinion.entity;

import jakarta.persistence.*;
import kg.nurtelecom.opinion.enums.ComplainStatus;
import kg.nurtelecom.opinion.enums.Reason;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "complains")
public class Complain extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private Reason reason;

    private String text;

    @CreationTimestamp
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private ComplainStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private Article articleId;

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public ComplainStatus getStatus() {
        return status;
    }

    public void setStatus(ComplainStatus status) {
        this.status = status;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Article getArticleId() {
        return articleId;
    }

    public void setArticleId(Article articleId) {
        this.articleId = articleId;
    }
}
