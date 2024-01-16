package kg.nurtelecom.opinion.payload.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;

public record PasswordResetRequest(
        @Size(min = 8, max = 200, message = "Пароль должен содержать от 8 до 200 символов")
        String password,
        @JsonProperty("confirm_password")
        String confirmPassword
) {
}
