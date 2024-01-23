package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.PasswordResetToken;

public interface EmailService {
    void sendPasswordResetToken(PasswordResetToken resetToken, String applicationUrl);

    void sendEmail(String to, String subject, String text, String from);
}
