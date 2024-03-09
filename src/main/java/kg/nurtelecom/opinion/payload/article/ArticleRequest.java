package kg.nurtelecom.opinion.payload.article;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.payload.tag.TagDTO;


import java.util.List;

public record ArticleRequest(

        @NotEmpty(message = "Статья должна иметь название")
        @Size(min = Article.TITLE_MIN_LENGTH, max = Article.TITLE_MAX_LENGTH, message = "Заголовок статьи должен быть от 3 до 120 символов")
        String title,

        @NotEmpty(message = "Статья должна иметь краткое описание ")
        @Size(min = Article.SHORT_DESCRIPTION_MIN_LENGTH, max = Article.SHORT_DESCRIPTION_MAX_LENGTH, message = "Описание статьи должно быть от 30 до 1000 символов")
        @JsonProperty("short_description")
        String shortDescription,


        @Size(min = Article.CONTENT_MIN_LENGTH, max = Article.CONTENT_MAX_LENGTH, message = "Контент  статьи должен быть от 500 до 40000 символов")
        String content,

        List<TagDTO> tags
) {
}
