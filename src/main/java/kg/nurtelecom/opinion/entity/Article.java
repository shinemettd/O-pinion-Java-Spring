package kg.nurtelecom.opinion.entity;

import jakarta.persistence.*;
import kg.nurtelecom.opinion.enums.ArticleStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "article")
public class Article extends Post {
    public static final int CONTENT_MAX_LENGTH = 40000;
    public static final int CONTENT_MIN_LENGTH = 500;
    public static final int TITLE_MAX_LENGTH = 120;
    public static final int TITLE_MIN_LENGTH = 3;
    public static final int SHORT_DESCRIPTION_MAX_LENGTH = 1000;
    public static final int SHORT_DESCRIPTION_MIN_LENGTH = 30;

    @Column(columnDefinition = "TEXT")
    private String shortDescription;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @Enumerated(EnumType.STRING)
    private ArticleStatus status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tags_articles",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

    public Article() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public ArticleStatus getStatus() {
        return status;
    }

    public void setStatus(ArticleStatus status) {
        this.status = status;
    }

    public Long getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(Long viewsCount) {
        this.viewsCount = viewsCount;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
