package kg.nurtelecom.opinion.payload.article;

import kg.nurtelecom.opinion.payload.tag.TagDTO;
import kg.nurtelecom.opinion.payload.user.UserResponse;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleGetDTO extends ArticlesGetDTO{
    private String content;
    private List<TagDTO> tags;


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
            String content,
            List<TagDTO> tags) {
        super(id, title, shortDescription, coverImage, dateTime, author, rating, totalFavourites, totalComments, viewsCount, inFavourites);
        this.content = content;
        this.tags = tags;
    }

    public String getContent() {
        return content;
    }

    public List<TagDTO> getTags() {
        return tags;
    }
}
