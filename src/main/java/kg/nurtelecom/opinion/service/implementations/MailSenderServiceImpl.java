package kg.nurtelecom.opinion.service.implementations;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kg.nurtelecom.opinion.entity.ConfirmationToken;
import kg.nurtelecom.opinion.entity.PasswordResetToken;
import kg.nurtelecom.opinion.enums.SourceType;
import kg.nurtelecom.opinion.exception.EmailSendingException;
import kg.nurtelecom.opinion.service.MailSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailSenderServiceImpl implements MailSenderService {
    private final JavaMailSender mailSender;
    @Value(value = "${spring.mail.username}")
    private String username;
    @Value(value = "${client-application.host}")
    private String clientApplicationHost;
    @Value(value = "${client-application.route.password.reset}")
    private String resetPasswordRoute;
    private String passwordResetUrl;

    public MailSenderServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @PostConstruct
    public void init() {
        passwordResetUrl = "http://" + clientApplicationHost + resetPasswordRoute;
    }

    @Override
    @Async
    public void sendPasswordResetEmail(PasswordResetToken resetToken) {
        MimeMessage message = mailSender.createMimeMessage();

        String firstName = resetToken.getUser().getFirstName();
        passwordResetUrl += "/" + resetToken.getToken();
        String content = getPasswordResetMailContent(firstName);

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

    private String getPasswordResetMailContent(String firstName) {
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
    public void sendEmail(String to, String sourceURL, String from, SourceType type) {
        MimeMessage message = mailSender.createMimeMessage();
        String content = type.equals(SourceType.ARTICLE) ?
                "Считаю, вам понравится эта статья >>>,<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">Просмотреть статью</a></h3>" :
                "Считаю, вам понравится это объявление >>>,<br>"
                        + "<h3><a href=\"[[URL]]\" target=\"_self\">Просмотреть объявление</a></h3>";


        content = content.replace("[[URL]]", sourceURL);
        try {
            message.setFrom(from);
            message.setRecipients(MimeMessage.RecipientType.TO, to);
            message.setSubject("Пользователь O!pinion " + from + " поделился с вами : ");
            message.setContent(content, "text/html; charset=utf-8");
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailSendingException("Ошибка при попыткe поделиться статьей/объявлением через email");
        }
    }

}
