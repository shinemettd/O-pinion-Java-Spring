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

    public ArticlesGetDTO(
            Long id,
            String title,
            String shortDescription,
            String coverImage,
            LocalDateTime dateTime,
            UserResponse author,
            Long rating,
            Long totalFavourites,
            Long totalComments,
            Long viewsCount,
            Boolean inFavourites)
    {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.coverImage = coverImage;
        this.dateTime = dateTime;
        this.author = author;
        this.rating = rating;
        this.totalFavourites = totalFavourites;
        this.totalComments = totalComments;
        this.viewsCount = viewsCount;
        this.inFavourites = inFavourites;
    }

    public Long getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }


    public String getShortDescription() {
        return shortDescription;
    }


    public String getCoverImage() {
        return coverImage;
    }


    public LocalDateTime getDateTime() {
        return dateTime;
    }


    public UserResponse getAuthor() {
        return author;
    }

    public Long getRating() {
        return rating;
    }


    public Long getTotalFavourites() {
        return totalFavourites;
    }


    public Long getTotalComments() {
        return totalComments;
    }


    public Long getViewsCount() {
        return viewsCount;
    }

    public Boolean getInFavourites() {
        return inFavourites;
    }

}
