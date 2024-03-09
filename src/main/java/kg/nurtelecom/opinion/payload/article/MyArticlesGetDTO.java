package kg.nurtelecom.opinion.payload.article;

import kg.nurtelecom.opinion.enums.ArticleStatus;
import kg.nurtelecom.opinion.payload.user.UserResponse;

import java.time.LocalDateTime;

public class MyArticlesGetDTO extends ArticlesGetDTO {
    private ArticleStatus status;
    public MyArticlesGetDTO(
            ArticleStatus status,
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
        super(id,title, shortDescription, coverImage, dateTime, author, rating, totalFavourites, totalComments, viewsCount, inFavourites);
        this.status = status;
    }

    public ArticleStatus getStatus() {
        return status;
    }
}
