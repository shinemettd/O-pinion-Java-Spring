package kg.nurtelecom.opinion.service.implementations;

import jakarta.servlet.http.HttpServletRequest;
import kg.nurtelecom.opinion.entity.PasswordResetToken;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.exception.NotValidException;
import kg.nurtelecom.opinion.payload.user.PasswordResetRequest;
import kg.nurtelecom.opinion.repository.PasswordResetTokenRepository;
import kg.nurtelecom.opinion.repository.UserRepository;
import kg.nurtelecom.opinion.service.EmailService;
import kg.nurtelecom.opinion.service.PasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordServiceImpl implements PasswordService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;

    public PasswordServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, PasswordResetTokenRepository tokenRepository, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    @Override
    public ResponseEntity<?> requestPasswordResetToken(String email, HttpServletRequest servletRequest) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь с такой почтой не найден"));

        PasswordResetToken resetToken = createPasswordResetToken(user);
        emailService.sendPasswordResetToken(resetToken, getApplicationUrl(servletRequest));
        return ResponseEntity.noContent().build();
    }

    @Override
    public PasswordResetToken createPasswordResetToken(User user) {
        String randomString = UUID.randomUUID().toString();

        Optional<PasswordResetToken> token = tokenRepository.findByUser(user);
        if (token.isPresent()) {
            token.get().setToken(randomString);
            token.get().setDates(LocalDateTime.now());
            return tokenRepository.save(token.get());
        } else {
            PasswordResetToken newToken = new PasswordResetToken(randomString, user);
            return tokenRepository.save(newToken);
        }
    }

    @Override
    public boolean isTokenExpired(PasswordResetToken token) {
        LocalDateTime currentDate = LocalDateTime.now();
        return currentDate.isAfter(token.getExpiredAt());
    }

    @Override
    public void updatePassword(String token, PasswordResetRequest passwordReset) {
        PasswordResetToken tokenEntity = tokenRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException("Ссылка недействительна"));

        if (isTokenExpired(tokenEntity)) {
            throw new NotValidException("Ссылка для сброса пароля истекла");
        }

        if (!passwordReset.password().equals(passwordReset.confirmPassword())) {
            throw new NotValidException("Пароли не совпадают");
        }

        User user = tokenEntity.getUser();
        user.setPassword(passwordEncoder.encode(passwordReset.password()));

        userRepository.save(user);
    }

    private String getApplicationUrl(HttpServletRequest servletRequest) {
        return "http://" + servletRequest.getServerName() + ":"
                + servletRequest.getServerPort() + servletRequest.getContextPath();
    }
}
