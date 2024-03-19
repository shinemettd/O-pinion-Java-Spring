package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.PasswordResetToken;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.password.PasswordResetRequest;
import kg.nurtelecom.opinion.payload.password.PasswordUpdateRequest;
import org.springframework.http.ResponseEntity;

public interface PasswordService {
    ResponseEntity<Void> updatePassword(User user, PasswordUpdateRequest passwordUpdateRequest);
    ResponseEntity<Void> validateResetToken(String token);
    ResponseEntity<Void> requestResetToken(String email);
    PasswordResetToken createResetToken(User user);
    ResponseEntity<Void> resetPassword(String token, PasswordResetRequest passwordResetRequest);
}