package kg.nurtelecom.opinion.payload.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import kg.nurtelecom.opinion.enums.Gender;

import java.util.Date;

public record UserSignUpRequest(
        @JsonProperty("first_name")
        @NotBlank(message = "Имя не может быть пустым")
        String firstName,
        @JsonProperty("last_name")
        @NotBlank(message = "Фамилия не может быть пустой")
        String lastName,
        @NotBlank(message = "Никнейм не должен быть пустым")
        @Size(min = 4, max = 20, message = "Никнейм должно содержать от 4 до 20 символов")
        String nickname,
        @Email(message = "Введите корректную почту")
        String email,
        @Size(min = 8, max = 200, message = "Пароль должен содержать от 8 до 200 символов")
        String password,
        @JsonProperty("confirm_password")
        String confirmPassword,
        Gender gender,
        String country,
        @JsonProperty("birth_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        @Schema(type = "string", format = "date", example = "2000-01-21")
        Date birthDate
) {
}
