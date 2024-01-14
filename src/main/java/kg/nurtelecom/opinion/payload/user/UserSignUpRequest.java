package kg.nurtelecom.opinion.payload.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import kg.nurtelecom.opinion.enums.Gender;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public record UserSignUpRequest(
        @NotBlank(message = "Имя не может быть пустым")
        String first_name,
        @NotBlank(message = "Фамилия не может быть пустой")
        String last_name,
        @NotBlank(message = "Никнейм не должно быть пустым")
        @Size(min = 4, max = 20, message = "Никнейм должно содержать от 2 до")
        String nickname,
        @Email(message = "Введите корректную почту")
        String email,
        @Size(min = 8, max = 200, message = "Пароль должен содержать от 8 до 200 символов")
        String password,
        String confirm_password,
        Gender gender,
        String country,
        Date birth_date
) {
}
