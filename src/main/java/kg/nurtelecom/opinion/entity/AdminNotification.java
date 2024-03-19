package kg.nurtelecom.opinion.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin_notifications")
public class AdminNotification extends Notification {

    public AdminNotification() {
    }

    public AdminNotification(String title, String content, Boolean read) {
        super(title, content, read);
    }
}
