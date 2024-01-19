package kg.nurtelecom.opinion.payload.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import kg.nurtelecom.opinion.payload.image.ImageResponse;
import kg.nurtelecom.opinion.payload.user.UserResponse;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleGetDTO extends ArticlesGetDTO{
    private String content;
    @JsonProperty("content_images")
    private List<ImageResponse> contentImages;


    public ArticleGetDTO() {

    }

    public List<ImageResponse> getContentImages() {
        return contentImages;
    }

    public void setContentImages(List<ImageResponse> contentImages) {
        this.contentImages = contentImages;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
