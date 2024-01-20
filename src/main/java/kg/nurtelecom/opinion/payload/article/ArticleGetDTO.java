package kg.nurtelecom.opinion.payload.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import kg.nurtelecom.opinion.payload.image.ImageResponse;
import kg.nurtelecom.opinion.payload.user.UserResponse;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleGetDTO extends ArticlesGetDTO{
    private String content;


    public ArticleGetDTO() {

    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
