package kg.nurtelecom.opinion.service.implementations;

import jakarta.servlet.http.HttpServletRequest;
import kg.nurtelecom.opinion.entity.ConfirmationToken;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.entity.UserPrivacySettings;
import kg.nurtelecom.opinion.enums.Role;
import kg.nurtelecom.opinion.enums.Status;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.exception.NotValidException;
import kg.nurtelecom.opinion.mapper.UserMapper;
import kg.nurtelecom.opinion.payload.user.UserSignInRequest;
import kg.nurtelecom.opinion.payload.user.UserSignInResponse;
import kg.nurtelecom.opinion.payload.user.UserSignUpRequest;
import kg.nurtelecom.opinion.payload.user.UserSignUpResponse;
import kg.nurtelecom.opinion.repository.ConfirmationTokenRepository;
import kg.nurtelecom.opinion.repository.UserPrivacyRepository;
import kg.nurtelecom.opinion.repository.UserRepository;
import kg.nurtelecom.opinion.service.AuthService;
import kg.nurtelecom.opinion.service.JwtService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserPrivacyRepository userPrivacyRepository;
    private final JwtService jwtService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailServiceImpl emailService;

    public AuthServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserPrivacyRepository userPrivacyRepository, JwtService jwtService, ConfirmationTokenRepository confirmationTokenRepository, EmailServiceImpl emailService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userPrivacyRepository = userPrivacyRepository;
        this.jwtService = jwtService;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailService = emailService;
    }

    @Override
    public ResponseEntity<UserSignUpResponse> signUp(UserSignUpRequest user, HttpServletRequest servletRequest) {
        if (!user.password().equals(user.confirmPassword())) {
            throw new NotValidException("Пароли не совпадают");
        }

        if ((LocalDate.now().minusYears(user.birthDate().getYear()).getYear() > 120) ||
            (LocalDate.now().minusYears(user.birthDate().getYear()).getYear() < 10)) {
            throw new NotValidException("Ваш возраст должен быть от 10 до 120 лет");
        }

        User userEntity = userMapper.toEntity(user);
        userEntity.setStatus(Status.NOT_VERIFIED);
        userEntity.setRole(Role.ROLE_USER);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        UserPrivacySettings userPrivacySettings = new UserPrivacySettings();
        userEntity.setPrivacySettings(userPrivacySettings);

        try {
            userEntity = userRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new NotValidException("Пользователь с такой почтой или никнеймом уже существует");
        }

        emailService.sendVerificationEmail(userEntity, servletRequest);
        return new ResponseEntity<>(userMapper.toModel(userEntity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UserSignInResponse> signIn(UserSignInRequest user) {
        User userEntity = userRepository.findByEmail(user.email())
                .orElseThrow(() -> new NotFoundException("Пользователь с такой почтой не найден"));

        if (!passwordEncoder.matches(user.password(), userEntity.getPassword())) {
            throw new NotValidException("Вы ввели неверный пароль");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.email(),
                        user.password()
                )
        );

        String jwtToken = jwtService.generateToken(userEntity);
        UserSignInResponse response = new UserSignInResponse(jwtToken);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<String> checkUserVerify(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException("Такого токена не существует"));

        if (isTokenExpired(confirmationToken)) {
            throw new NotValidException("Ссылка для сброса пароля истекла");
        }
        User userEntity = confirmationToken.getUser();
        userEntity.setStatus(Status.VERIFIED);
        userRepository.save(userEntity);
        return ResponseEntity.ok("Вы подтвердили свой аккаунт. Перейдите на страницу входа.");
    }

    private boolean isTokenExpired(ConfirmationToken token) {
        LocalDateTime currentDate = LocalDateTime.now();
        return currentDate.isAfter(token.getExpiredAt());
    }
}
