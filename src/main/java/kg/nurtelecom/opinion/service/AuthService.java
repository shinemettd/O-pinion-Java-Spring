package kg.nurtelecom.opinion.service;

import jakarta.servlet.http.HttpServletRequest;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.user.UserSignInRequest;
import kg.nurtelecom.opinion.payload.user.UserSignInResponse;
import kg.nurtelecom.opinion.payload.user.UserSignUpRequest;
import kg.nurtelecom.opinion.payload.user.UserSignUpResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<UserSignUpResponse> signUp(UserSignUpRequest user, HttpServletRequest servletRequest);
    void sendConfirmationToken(User user, HttpServletRequest request);
    ResponseEntity<UserSignInResponse> signIn(UserSignInRequest user);
    ResponseEntity<String> checkUserVerify(String token);
}
