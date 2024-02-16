package kg.nurtelecom.opinion.payload.article;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ArticleRequest(

        @NotEmpty(message = "Статья должна иметь название")
        @Size(min = 3, max = 120, message = "Заголовок статьи должен быть от 3 до 120 символов")
        String title,

        @NotEmpty(message = "Статья должна иметь краткое описание ")
        @Size(min = 30, max = 1000, message = "Описание статьи должно быть от 30 до 1000 символов")
        @JsonProperty("short_description")
        String shortDescription,
        @JsonProperty("cover_image")
        String coverImage,

        @Size(min = 3500, max = 40000, message = "Контент  статьи должен быть от 3500 до 40000 символов")
        String content
) {
}
