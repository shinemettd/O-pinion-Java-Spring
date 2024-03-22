package kg.nurtelecom.opinion.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "saved_announcements")
public class SavedAnnouncement extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "announcement_id", referencedColumnName = "id")
    private Announcement announcement;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public SavedAnnouncement() {
    }

    public SavedAnnouncement(Announcement announcement, User user) {
        this.announcement = announcement;
        this.user = user;
    }

    public Announcement getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
