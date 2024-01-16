package kg.nurtelecom.opinion.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "confirmation_tokens")
public class ConfirmationToken extends Token {
    public ConfirmationToken(String token, User user) {
        super(token, user);
    }

    public ConfirmationToken() {
        super();
    }
}
