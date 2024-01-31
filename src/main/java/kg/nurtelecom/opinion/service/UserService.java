package kg.nurtelecom.opinion.service;


import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.user.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    ResponseEntity<GetUserProfileDTO> getUserProfile(Long userId);
    ResponseEntity<GetUserProfileDTO> getUserProfileByNick(String userNick);

    ResponseEntity<Void> deleteUserAccount(User user);

    ResponseEntity<Void> restoreUserAccount(User user);

    ResponseEntity<GetUserResponse> getMyProfile(User user);

    ResponseEntity<GetUserResponse> updateUser(Long userId, UserUpdateRequest userRequest);

    ResponseEntity<UserUpdateEmailResponse> updateUserEmail(Long userId, UserUpdateEmailRequest userRequest);

}
