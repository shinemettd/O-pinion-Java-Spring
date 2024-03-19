package kg.nurtelecom.opinion.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "admin_notifications")
public class AdminNotification extends Notification {

    public AdminNotification() {
    }

    public AdminNotification(String title, String content, LocalDateTime dateTime, Boolean read) {
        super(title, content, dateTime, read);
    }
}
