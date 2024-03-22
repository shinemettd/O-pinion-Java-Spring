package kg.nurtelecom.opinion.payload.announcement;

import com.fasterxml.jackson.annotation.JsonProperty;
import kg.nurtelecom.opinion.enums.AccessType;

import java.time.LocalDateTime;

public class AnnouncementResponse {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("content")
    private String content;
    @JsonProperty("cover_image")
    private String coverImage;
    @JsonProperty("date_time")
    private LocalDateTime dateTime;
    @JsonProperty("total_views")
    private Long totalViews;

    @JsonProperty("total_favourites")
    private Long totalFavourites;
    @JsonProperty("total_comments")
    private Long totalComments;
    @JsonProperty("in_favourites")
    private boolean inFavourites;
    @JsonProperty("access_type")
    private AccessType accessType;

    public AnnouncementResponse(
            Long id,
            String title,
            String content,
            String coverImage,
            LocalDateTime dateTime,
            Long totalViews,
            Long totalFavourites,
            Long totalComments,
            boolean inFavourites,
            AccessType accessType
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.coverImage = coverImage;
        this.dateTime = dateTime;
        this.totalViews = totalViews;
        this.totalFavourites = totalFavourites;
        this.totalComments = totalComments;
        this.inFavourites = inFavourites;
        this.accessType = accessType;
    }


}
