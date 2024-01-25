package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.entity.UserPrivacySettings;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.mapper.UserMapper;
import kg.nurtelecom.opinion.payload.user.GetUserProfileDTO;
import kg.nurtelecom.opinion.repository.UserPrivacyRepository;
import kg.nurtelecom.opinion.repository.UserRepository;
import kg.nurtelecom.opinion.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserPrivacyRepository userPrivacyRepository;
    private final UserMapper userMapper;


    public UserServiceImpl(UserRepository userRepository, UserPrivacyRepository userPrivacyRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userPrivacyRepository = userPrivacyRepository;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseEntity<GetUserProfileDTO> getUserProfile(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new NotFoundException("Пользователя с таким id не существует");
        }
        User userEntity = user.get();
        UserPrivacySettings userPrivacySettings = userPrivacyRepository.getReferenceById(userId);
        GetUserProfileDTO userResponse = new GetUserProfileDTO(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getNickname(),
                userEntity.getEmail(),
                userEntity.getAvatar(),
                userEntity.getBirthDate(),
                userPrivacySettings.isFirstNameVisible(),
                userPrivacySettings.isLastNameVisible(),
                userPrivacySettings.isEmailVisible(),
                userPrivacySettings.isBirthDateVisible()

        );
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
