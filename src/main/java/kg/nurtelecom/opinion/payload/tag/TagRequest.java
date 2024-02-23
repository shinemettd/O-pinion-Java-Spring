package kg.nurtelecom.opinion.payload.tag;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record TagRequest(
        @NotEmpty(message = "У тега должно быть название ")
        @Size(min = 2, max = 30 , message = "Имя тега должно быть от 3 до 30 символов ")
        String name
) {
}
