package kg.nurtelecom.opinion.service;

import jakarta.servlet.http.HttpServletRequest;
import kg.nurtelecom.opinion.entity.PasswordResetToken;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.user.PasswordResetRequest;
import org.springframework.http.ResponseEntity;

public interface PasswordService {
    ResponseEntity<?> requestPasswordResetToken(String email, HttpServletRequest servletRequest);
    PasswordResetToken createPasswordResetToken(User user);
    boolean isTokenExpired(PasswordResetToken token);
    void updatePassword(String passwordResetToken, PasswordResetRequest passwordResetRequest);
}