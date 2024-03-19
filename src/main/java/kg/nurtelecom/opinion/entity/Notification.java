package kg.nurtelecom.opinion.entity;

import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class Notification extends BaseEntity {
    String title;
    String content;
    @CreationTimestamp
    LocalDateTime dateTime;
    Boolean read;

    public Notification() {
    }

    public Notification(String title, String content, LocalDateTime dateTime, Boolean read) {
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
        this.read = read;
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
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }
}
