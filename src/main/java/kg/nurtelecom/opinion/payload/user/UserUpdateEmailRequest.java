package kg.nurtelecom.opinion.payload.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserUpdateEmailRequest (
        @NotBlank(message = "Почта не может быть пустой")
        @Email(message = "Введите корректную почту")
        String email
){
}
