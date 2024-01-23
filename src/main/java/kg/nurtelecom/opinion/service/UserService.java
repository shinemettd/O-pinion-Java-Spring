package kg.nurtelecom.opinion.service;


import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.user.GetUserResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<GetUserResponse> getUserProfile(Long userId);
}
