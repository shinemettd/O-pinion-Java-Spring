package kg.nurtelecom.opinion.payload.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.Date;

public record UserUpdateRequest(
        @JsonProperty("first_name")
        @NotBlank(message = "Имя не может быть пустым")
        @Pattern(regexp = "^[А-Яа-яЁё]+$", message = "Имя должно содержать только кириллические буквы")
        String firstName,
        @JsonProperty("last_name")
        @NotBlank(message = "Фамилия не может быть пустой")
        @Pattern(regexp = "^[А-Яа-яЁё]+$", message = "Фамилия должна содержать только кириллические буквы")
        String lastName,
        @NotBlank(message = "Никнейм не должен быть пустым")
        @Size(min = 4, max = 20, message = "Никнейм должен содержать от 4 до 20 символов")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Никнейм может содержать буквы, цифры и нижнее подчеркивание")
        String nickname,
        @NotNull(message = "Дата рождения обязательна")
        @Past(message = "Дата рождения должна быть в прошлом")
        @JsonProperty("birth_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        @Schema(type = "string", format = "date", example = "2000-01-21")
        Date birthDate
) {
}
