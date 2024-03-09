package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.nurtelecom.opinion.payload.user.UserSignInRequest;
import kg.nurtelecom.opinion.payload.user.UserSignInResponse;
import kg.nurtelecom.opinion.payload.user.UserSignUpRequest;
import kg.nurtelecom.opinion.payload.user.UserSignUpResponse;
import kg.nurtelecom.opinion.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(
        name = "Контроллер для авторизации/регистрации",
        description = "В этом контроллере есть возможности авторизации, регистрации"
)
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign-up")
    @Operation(
            summary = "Регистрация нового аккаунта"
    )
    public ResponseEntity<UserSignUpResponse> signUp(
            @Valid @RequestBody UserSignUpRequest user,
            HttpServletRequest servletRequest
    ) {
        return authService.signUp(user, servletRequest);
    }

    @PostMapping("/sign-in")
    @Operation(
            summary = "Вход в аккаунт"
    )
    public ResponseEntity<UserSignInResponse> authenticate(@RequestBody UserSignInRequest user) {
        return authService.signIn(user);
    }

    @GetMapping("/verify")
    @Operation(
            summary = "Проверка пользователя через почту"
    )
    public ResponseEntity<String> verificationByEmail(@RequestParam("token") String token){
        return authService.checkUserVerify(token);
    }
}
