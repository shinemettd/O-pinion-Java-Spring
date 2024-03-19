package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.PasswordResetToken;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.exception.NotValidException;
import kg.nurtelecom.opinion.payload.password.PasswordResetRequest;
import kg.nurtelecom.opinion.payload.password.PasswordUpdateRequest;
import kg.nurtelecom.opinion.repository.PasswordResetTokenRepository;
import kg.nurtelecom.opinion.repository.UserRepository;
import kg.nurtelecom.opinion.service.MailSenderService;
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
    private final MailSenderService mailSenderService;

    public PasswordServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, PasswordResetTokenRepository tokenRepository, MailSenderService mailSenderService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.mailSenderService = mailSenderService;
    }

    @Override
    public ResponseEntity<Void> updatePassword(User user, PasswordUpdateRequest passwordUpdateRequest) {
        if (!passwordEncoder.matches(passwordUpdateRequest.oldPassword(), user.getPassword())) {
            throw new NotValidException("Неверный пароль");
        }
        if (passwordEncoder.matches(passwordUpdateRequest.newPassword(), user.getPassword())) {
            throw new NotValidException("Новый пароль совпадает со старым");
        }
        if (!passwordUpdateRequest.newPassword().equals(passwordUpdateRequest.confirmNewPassword())) {
            throw new NotValidException("Пароли не совпадают");
        }

        user.setPassword(passwordEncoder.encode(passwordUpdateRequest.newPassword()));
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> validateResetToken(String token) {
        PasswordResetToken tokenEntity = tokenRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException("Ссылка недействительна"));

        if (isTokenExpired(tokenEntity)) {
            throw new NotValidException("Ссылка для сброса пароля истекла");
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> requestResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь с такой почтой не найден"));

        PasswordResetToken resetToken = createResetToken(user);
        mailSenderService.sendPasswordResetEmail(resetToken);

        return ResponseEntity.ok().build();
    }

    @Override
    public PasswordResetToken createResetToken(User user) {
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
    public ResponseEntity<Void> resetPassword(String token, PasswordResetRequest passwordResetRequest) {
        PasswordResetToken tokenEntity = tokenRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException("Ссылка недействительна"));

        if (!passwordResetRequest.password().equals(passwordResetRequest.confirmPassword())) {
            throw new NotValidException("Пароли не совпадают");
        }

        User user = tokenEntity.getUser();
        user.setPassword(passwordEncoder.encode(passwordResetRequest.password()));

        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    private boolean isTokenExpired(PasswordResetToken token) {
        LocalDateTime currentDate = LocalDateTime.now();
        return currentDate.isAfter(token.getExpiredAt());
    }
}

