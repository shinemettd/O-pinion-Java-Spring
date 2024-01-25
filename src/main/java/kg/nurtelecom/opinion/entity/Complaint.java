package kg.nurtelecom.opinion.entity;

import jakarta.persistence.*;
import kg.nurtelecom.opinion.enums.ComplaintStatus;
import kg.nurtelecom.opinion.enums.Reason;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "complaints")
public class Complaint extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private Reason reason;

    private String text;

    @CreationTimestamp
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private ComplaintStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private Article article;

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

    public ComplaintStatus getStatus() {
        return status;
    }

    public void setStatus(ComplaintStatus status) {
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
}
