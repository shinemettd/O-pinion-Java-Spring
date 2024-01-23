package kg.nurtelecom.opinion.service.implementations;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kg.nurtelecom.opinion.entity.PasswordResetToken;
import kg.nurtelecom.opinion.exception.EmailSendingException;
import kg.nurtelecom.opinion.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {
    @Value(value = "${spring.mail.username}")
    private String senderEmail;

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void sendPasswordResetToken(PasswordResetToken resetToken, String applicationUrl) {
        MimeMessage message = mailSender.createMimeMessage();

        String content = "Уважаемый/-ая [[name]],<br>"
                + "[Вы недавно запросили ссылку для сброса пароля]<br>"
                + "Пожалуйста, пройдите по ссылке, чтобы завершить действие.<br>"
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
            throw new EmailSendingException("Ошибка при попыткe отправить письмо");
        }

        mailSender.send(message);
    }

    public void sendEmail(String to, String articleURL,  String from) {
        MimeMessage message = mailSender.createMimeMessage();
        String content = "Считаю, вам понравится эта статья >>>,<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">Просмотреть статью</a></h3>";

        content = content.replace("[[URL]]", articleURL);
        try {
            message.setFrom(from);
            message.setRecipients(MimeMessage.RecipientType.TO, to);
            message.setSubject("Пользователь O!pinion " + from + " поделился с вами статьей ");
            message.setContent(content, "text/html; charset=utf-8");
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailSendingException("Ошибка при попыткe поделиться статьей через email");
        }
    }
}
