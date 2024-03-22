package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.ConfirmationToken;
import kg.nurtelecom.opinion.entity.PasswordResetToken;
import kg.nurtelecom.opinion.enums.SourceType;

public interface MailSenderService {
    void sendPasswordResetEmail(PasswordResetToken resetToken);
    void sendConfirmationEmail(ConfirmationToken confirmationToken, String confirmationUrl);
    void sendEmail(String to, String text, String from, SourceType type);
}
