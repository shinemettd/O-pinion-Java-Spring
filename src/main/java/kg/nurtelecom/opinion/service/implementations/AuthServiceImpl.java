package kg.nurtelecom.opinion.service.implementations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
import kg.nurtelecom.opinion.repository.UserPrivacyRepository;
import kg.nurtelecom.opinion.repository.UserRepository;
import kg.nurtelecom.opinion.service.AuthService;
import kg.nurtelecom.opinion.service.JwtService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserPrivacyRepository userPrivacyRepository;
    private final JwtService jwtService;

    @Value(value = "${spring.mail.username}")
    private String senderEmail;
    @Value("${app.jwtSecret}")
    private String jwtSecret;
    private final JavaMailSender mailSender;

    public AuthServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserPrivacyRepository userPrivacyRepository, JwtService jwtService, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userPrivacyRepository = userPrivacyRepository;
        this.jwtService = jwtService;
        this.mailSender = mailSender;
    }

    @Override
    public ResponseEntity<UserSignUpResponse> signUp(UserSignUpRequest user) {
        if (!user.password().equals(user.confirmPassword())) {
            throw new NotValidException("Пароли не совпадают");
        }

        if (LocalDate.now().minusYears(user.birthDate().getYear()).getYear() > 120) {
            throw new NotValidException("Возраст не может быть больше 120");
        }

        User userEntity = userMapper.toEntity(user);
        userEntity.setStatus(Status.NOT_VERIFIED);
        userEntity.setRole(Role.ROLE_USER);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        UserPrivacySettings userPrivacySettings = new UserPrivacySettings();
        userEntity.setPrivacySettings(userPrivacySettings);

        sendVerificationEmail(userEntity.getEmail(), userEntity.getId());

        try {
            userEntity = userRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new NotValidException("Пользователь с такой почтой или никнеймом уже существует");
        }

        return ResponseEntity.ok(userMapper.toModel(userEntity));
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

    public ResponseEntity<?> checkUserVerify(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(jwtSecret.getBytes())
                    .build()
                    .parseClaimsJws(token);

            String userId = claimsJws.getBody().getSubject();

            User userEntity = userRepository.findById(Long.parseLong(userId))
                    .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

            userEntity.setStatus(Status.VERIFIED);
            userRepository.save(userEntity);


            return ResponseEntity.ok(userMapper.toModel(userEntity));
        } catch (Exception e) {
            // Todo: Created exception for invalid token
            return ResponseEntity.badRequest().body("Неверный токен верификации");
        }
    }

    private void sendVerificationEmail(String userEmail, Long userId) {
        String token = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        String verificationUrl = "http:///api/auth/verify?token=" + token;
        System.out.println();
        System.out.println(verificationUrl);
        System.out.println();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(userEmail);
        message.setSubject("Подтверждение Email");
        message.setText("Для завершения регистрации перейдите по следующей ссылке: " + verificationUrl);
        mailSender.send(message);
    }
}
