package kg.nurtelecom.opinion.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken extends Token {

    public PasswordResetToken(String token, User user) {
        super(token, user);
    }

    public PasswordResetToken() {
        super();
    }
}
