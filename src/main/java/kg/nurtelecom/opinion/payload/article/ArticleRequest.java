package kg.nurtelecom.opinion.payload.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ArticleRequest(

        @NotEmpty(message = "Это поле не должно быть пустым ")
        String title,

        @NotEmpty(message = "Статья должна иметь краткое описание ")
        @Size(min = 30 , max = 1000 , message = "Описание статьи должно быть от 30 до 1000 символов")
        @JsonProperty("short_description")
        String shortDescription,

        @NotEmpty(message = "Статья должна содержать контент")
        @Size(min = 30, max = 5000, message = "Контент статьи должен содежать от 30 до 5000 символов")
        String content
) {
}
