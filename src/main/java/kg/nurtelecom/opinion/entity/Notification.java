package kg.nurtelecom.opinion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class Notification extends BaseEntity {
    String title;
    @Column(columnDefinition = "TEXT")
    String content;
    String url;
    @CreationTimestamp
    LocalDateTime dateTime;
    Boolean isRead;

    public Notification() {
    }

    public Notification(String title, String content, String url, Boolean isRead) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.isRead = isRead;
    }

    public Notification(String title, String content, Boolean isRead) {
        this.title = title;
        this.content = content;
        this.isRead = isRead;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
