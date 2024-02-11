package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.ConfirmationToken;
import kg.nurtelecom.opinion.entity.PasswordResetToken;

public interface MailSenderService {
    void sendPasswordResetEmail(PasswordResetToken resetToken, String passwordResetUrl);
    void sendConfirmationEmail(ConfirmationToken confirmationToken, String confirmationUrl);

    void sendEmail(String to, String text, String from);
}
