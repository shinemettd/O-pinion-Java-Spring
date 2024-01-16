package kg.nurtelecom.opinion.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@MappedSuperclass
public class Token extends BaseEntity {
    private static final int EXPIRATION_TIME = 10;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Token(String token, User user) {
        this.token = token;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        expiredAt = createdAt.plus(EXPIRATION_TIME, ChronoUnit.MINUTES);
    }

    public Token() {
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public User getUser() {
        return user;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setDates(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        expiredAt = createdAt.plus(EXPIRATION_TIME, ChronoUnit.MINUTES);
    }
}
