package kg.nurtelecom.opinion.payload.complain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import kg.nurtelecom.opinion.enums.Reason;

public record ComplainDTO(
        @JsonProperty("reason")
        Reason reason,

        @JsonProperty("text")
        @NotEmpty(message = "Жалоба должна иметь заполнена")
        @Size(min = 10, max = 1000, message = "Описание жалобы должно быть от 10 до 1000 символов")
        String text
) {
}
