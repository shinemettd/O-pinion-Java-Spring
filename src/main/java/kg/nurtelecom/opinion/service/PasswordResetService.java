package kg.nurtelecom.opinion.service;

import jakarta.servlet.http.HttpServletRequest;
import kg.nurtelecom.opinion.entity.PasswordResetToken;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.user.PasswordResetRequest;
import kg.nurtelecom.opinion.payload.user.PasswordResetTokenResponse;
import org.springframework.http.ResponseEntity;

public interface PasswordResetService {
    ResponseEntity<PasswordResetTokenResponse> getPasswordResetToken(String email, HttpServletRequest servletRequest);
    PasswordResetToken createPasswordResetToken(User user);
    boolean isTokenExpired(PasswordResetToken token);
    void updatePassword(String passwordResetToken, PasswordResetRequest passwordResetRequest);
}