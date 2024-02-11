package kg.nurtelecom.opinion.service.implementations;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kg.nurtelecom.opinion.entity.ConfirmationToken;
import kg.nurtelecom.opinion.entity.PasswordResetToken;
import kg.nurtelecom.opinion.exception.EmailSendingException;
import kg.nurtelecom.opinion.service.MailSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailSenderServiceImpl implements MailSenderService {
    @Value(value = "${spring.mail.username}")
    private String username;

    private final JavaMailSender mailSender;

    public MailSenderServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void sendPasswordResetEmail(PasswordResetToken resetToken, String passwordResetUrl) {
        MimeMessage message = mailSender.createMimeMessage();

        String firstName = resetToken.getUser().getFirstName();
        String content = getMailContent(firstName, passwordResetUrl);

        try {
            message.setFrom(username);
            message.setRecipients(MimeMessage.RecipientType.TO, resetToken.getUser().getEmail());
            message.setSubject("Password Reset");
            message.setContent(content, "text/html; charset=utf-8");
        } catch (MessagingException e) {
            throw new EmailSendingException("Ошибка при попыткe отправить письмо");
        }

        mailSender.send(message);
    }

    @Override
    public void sendConfirmationEmail(ConfirmationToken confirmationToken, String confirmationUrl) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(confirmationToken.getUser().getEmail());
        message.setSubject("Подтверждение аккаунта");
        message.setText("Для завершения регистрации перейдите по следующей ссылке: " + confirmationUrl);
        mailSender.send(message);
    }

    private String getMailContent(String firstName, String passwordResetUrl) {
        String content = "Уважаемый/-ая [[name]],<br>"
                + "[Вы недавно запросили ссылку для сброса пароля]<br>"
                + "Пожалуйста, пройдите по ссылке, чтобы завершить действие.<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">RESET PASSWORD</a></h3>"
                + "С уважением,<br>"
                + "O!pinion Service.";

        content = content.replace("[[name]]", firstName);
        return content.replace("[[URL]]", passwordResetUrl);
    }

    @Override
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
