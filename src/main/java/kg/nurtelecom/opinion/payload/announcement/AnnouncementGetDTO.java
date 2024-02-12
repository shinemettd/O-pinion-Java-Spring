package kg.nurtelecom.opinion.payload.announcement;

import com.fasterxml.jackson.annotation.JsonProperty;
import kg.nurtelecom.opinion.enums.AccessType;

import java.time.LocalDateTime;

public class AnnouncementGetDTO {

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

    @JsonProperty("access_type")
    private AccessType accessType;

    public AnnouncementGetDTO(String title, String content, String coverImage, LocalDateTime dateTime, Long totalViews, AccessType accessType) {
        this.title = title;
        this.content = content;
        this.coverImage = coverImage;
        this.dateTime = dateTime;
        this.totalViews = totalViews;
        this.accessType = accessType;
    }

    @Override
    public String toString() {
        return "AnnouncementGetDTO{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", coverImage='" + coverImage + '\'' +
                ", dateTime=" + dateTime +
                ", totalViews=" + totalViews +
                '}';
    }
}