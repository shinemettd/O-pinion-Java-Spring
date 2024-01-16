package kg.nurtelecom.opinion.service.implementations;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kg.nurtelecom.opinion.entity.PasswordResetToken;
import kg.nurtelecom.opinion.exception.EmailSendingError;
import kg.nurtelecom.opinion.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Value(value = "${spring.mail.username}")
    private String senderEmail;

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendPasswordResetToken(PasswordResetToken resetToken, String applicationUrl) {
        MimeMessage message = mailSender.createMimeMessage();

        String content = "Уважаемый/-ая [[name]],<br>"
                + "[Вы недавно запросили ссылку для сброса пароля]<br>"
                + "Пожалуйста, пройдите по ссылке, чтобы заврешить действие.<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">RESET PASSWORD</a></h3>"
                + "С уважением,<br>"
                + "O!pinion Service.";

        content = content.replace("[[name]]", resetToken.getUser().getFirstName());

        String resetURL = applicationUrl + "/api/password/reset/" + resetToken.getToken();
        content = content.replace("[[URL]]", resetURL);

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, resetToken.getUser().getEmail());
            message.setSubject("Password Reset");
            message.setContent(content, "text/html; charset=utf-8");
        } catch (MessagingException e) {
            throw new EmailSendingError("Ошибка при попытка отправить письмо");
        }

        mailSender.send(message);
    }
}
