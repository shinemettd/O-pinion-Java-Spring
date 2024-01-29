package kg.nurtelecom.opinion.service;


import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.user.GetUserProfileDTO;
import kg.nurtelecom.opinion.payload.user.GetUserResponse;
import kg.nurtelecom.opinion.payload.user.UserUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    ResponseEntity<GetUserProfileDTO> getUserProfile(Long userId);

    ResponseEntity<Void> deleteUserAccount(User user);

    ResponseEntity<Void> restoreUserAccount(User user);

    ResponseEntity<GetUserResponse> getMyProfile(User user);

    ResponseEntity<GetUserResponse> updateUser(Long userId, UserUpdateRequest userRequest);

}
