package kg.nurtelecom.opinion.payload.password;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PasswordUpdateRequest(
        @JsonProperty("old_password")
        String oldPassword,
        @JsonProperty("new_password")
        @NotBlank(message = "Пароль не должен быть пустым")
        @Size(min = 8, max = 200, message = "Пароль должен содержать от 8 до 100 символов")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[A-Z]).*$", message = "Пароль должен содержать минимум 1 заглавную букву, 1 цифру и 1 букву")
        String newPassword,
        @JsonProperty("confirm_new_password")
        String confirmNewPassword
) {
}
