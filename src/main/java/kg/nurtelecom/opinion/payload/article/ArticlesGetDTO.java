package kg.nurtelecom.opinion.payload.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.ArticleStatus;
import kg.nurtelecom.opinion.payload.user.UserResponse;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

public class ArticlesGetDTO {
    private Long id;
    private String title;
    @JsonProperty("short_description")
    private String shortDescription;

    @JsonProperty("cover_image")
    private String coverImage;

    @JsonProperty("date_time")
    private LocalDateTime dateTime;

    private UserResponse author;


    private Long rating;

    @JsonProperty("total_favourites")
    private Long totalFavourites;

    @JsonProperty("total_comments")
    private Long totalComments;

    @JsonProperty("total_views")
    private Long viewsCount;
    @JsonProperty("in_favourites")
    private Boolean inFavourites;

    public ArticlesGetDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public UserResponse getAuthor() {
        return author;
    }

    public void setAuthor(UserResponse author) {
        this.author = author;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Long getTotalFavourites() {
        return totalFavourites;
    }

    public void setTotalFavourites(Long totalFavourites) {
        this.totalFavourites = totalFavourites;
    }

    public Long getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(Long totalComments) {
        this.totalComments = totalComments;
    }

    public Long getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(Long viewsCount) {
        this.viewsCount = viewsCount;
    }

    public Boolean getInFavourites() {
        return inFavourites;
    }

    public void setInFavourites(Boolean inFavourites) {
        this.inFavourites = inFavourites;
    }
}
