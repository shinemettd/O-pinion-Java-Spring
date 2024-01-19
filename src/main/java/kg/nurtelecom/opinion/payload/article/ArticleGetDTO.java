package kg.nurtelecom.opinion.payload.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import kg.nurtelecom.opinion.payload.image.ImageResponse;
import kg.nurtelecom.opinion.payload.user.UserResponse;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleGetDTO {
    private Long id;
    private String title;
    @JsonProperty("short_description")
    private String shortDescription;

    private String content;
    @JsonProperty("cover_image")
    private String coverImage;

    @JsonProperty("content_images")
    List<ImageResponse> contentImages;

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

    public ArticleGetDTO() {

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

    public List<ImageResponse> getContentImages() {
        return contentImages;
    }

    public void setContentImages(List<ImageResponse> contentImages) {
        this.contentImages = contentImages;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
