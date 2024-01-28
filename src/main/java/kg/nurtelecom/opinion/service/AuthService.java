package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.payload.user.UserSignInRequest;
import kg.nurtelecom.opinion.payload.user.UserSignInResponse;
import kg.nurtelecom.opinion.payload.user.UserSignUpRequest;
import kg.nurtelecom.opinion.payload.user.UserSignUpResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<UserSignUpResponse> signUp(UserSignUpRequest user);
    ResponseEntity<UserSignInResponse> signIn(UserSignInRequest user);
    ResponseEntity<?> checkUserVerify(String token);
}
