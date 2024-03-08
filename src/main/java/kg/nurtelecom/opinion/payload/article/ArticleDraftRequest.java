package kg.nurtelecom.opinion.payload.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import kg.nurtelecom.opinion.payload.tag.TagDTO;

import java.util.List;

public record ArticleDraftRequest(
        @Size(max = 120, message = "Заголовок статьи должен быть до 120 символов")
        String title,


        @Size(max = 1000, message = "Описание статьи должно быть до 1000 символов")
        @JsonProperty("short_description")
        String shortDescription,


        @Size(max = 40000, message = "Контент  статьи должен быть от 40000 символов")
        String content,

        List<TagDTO> tags
) {
}
