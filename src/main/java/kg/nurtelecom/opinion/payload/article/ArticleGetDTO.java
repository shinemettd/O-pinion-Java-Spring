package kg.nurtelecom.opinion.payload.article;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.user.UserResponse;

import java.time.LocalDateTime;

public class ArticleGetDTO extends ArticlesGetDTO{
    private String content;


    public ArticleGetDTO(
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
            Boolean inFavourites,
            String content
    ) {
        super(id, title, shortDescription, coverImage, dateTime, author, rating, totalFavourites, totalComments, viewsCount, inFavourites);
        this.content = content;
    }

    public String getContent() {
        return content;
    }
    
}
