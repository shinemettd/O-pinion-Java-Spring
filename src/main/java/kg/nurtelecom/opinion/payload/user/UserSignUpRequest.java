package kg.nurtelecom.opinion.payload.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserSignUpRequest(
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
        @NotBlank(message = "Почта не может быть пустой")
        @Email(message = "Введите корректную почту")
        String email,
        @NotBlank(message = "Пароль не должен быть пустым")
        @Size(min = 8, max = 200, message = "Пароль должен содержать от 8 до 200 символов")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[A-Z]).*$", message = "Пароль должен содержать минимум 1 заглавную букву, 1 цифру и 1 букву")
        String password,
        @JsonProperty("confirm_password")
        String confirmPassword,

        @NotNull(message = "Дата рождения обязательна")
        @JsonProperty("birth_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        @Past(message = "Дата рождения должна быть в прошлом")
        @Schema(type = "string", format = "date", example = "2000-01-21")
        LocalDate birthDate
) {
}
