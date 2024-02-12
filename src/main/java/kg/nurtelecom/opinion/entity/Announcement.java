package kg.nurtelecom.opinion.entity;

import kg.nurtelecom.opinion.enums.AccessType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "announcements")
public class Announcement extends Post {
    @Enumerated(EnumType.STRING)
    private AccessType accessType;

    public Announcement() {

    }

    public Announcement(String title, String content, String coverImage, LocalDateTime dateTime, Long viewsCount, AccessType accessType) {
        this.title = title;
        this.content = content;
        this.coverImage = coverImage;
        this.dateTime = dateTime;
        this.viewsCount = viewsCount;
        this.accessType = accessType;
    }
    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }
}
