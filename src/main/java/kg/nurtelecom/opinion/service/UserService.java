package kg.nurtelecom.opinion.service;


import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.user.GetUserProfileDTO;
import kg.nurtelecom.opinion.payload.user.GetUserResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<GetUserProfileDTO> getUserProfile(Long userId);

    ResponseEntity<Void> deleteUserAccount(User user);

    ResponseEntity<Void> restoreUserAccount(User user);
}
