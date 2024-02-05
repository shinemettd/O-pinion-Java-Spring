package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.entity.UserPrivacySettings;
import kg.nurtelecom.opinion.enums.Status;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.mapper.UserMapper;
import kg.nurtelecom.opinion.payload.user.*;
import kg.nurtelecom.opinion.repository.UserPrivacyRepository;
import kg.nurtelecom.opinion.repository.UserRepository;
import kg.nurtelecom.opinion.service.JwtService;
import kg.nurtelecom.opinion.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserPrivacyRepository userPrivacyRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;


    public UserServiceImpl(UserRepository userRepository, UserPrivacyRepository userPrivacyRepository, UserMapper userMapper, JwtService jwtService) {
        this.userRepository = userRepository;
        this.userPrivacyRepository = userPrivacyRepository;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    @Override
    public ResponseEntity<GetUserResponse> getMyProfile(User user) {
        checkUserStatus(user.getStatus());
        return new ResponseEntity<>(userMapper.toGetUserResponse(user) ,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetUserResponse> getUserProfile(Long userId) {
        Optional<User> user = userRepository.findByIdAndStatus(userId, Status.VERIFIED);
        User userEntity = user.orElseThrow(() -> new NotFoundException("Пользователя с таким id не существует"));
        UserPrivacySettings userPrivacySettings = userPrivacyRepository.getUserPrivacySettingsByUser(userEntity).get();

        User userResponse = setNecessaryFields(userPrivacySettings, userEntity);
        return new ResponseEntity<>(userMapper.toGetUserResponse(userResponse), HttpStatus.OK);
    }

    private User setNecessaryFields(UserPrivacySettings userPrivacySettings, User userEntity) {
        User userResponse = new User();
        userResponse.setId(userEntity.getId());
        userResponse.setNickname(userEntity.getNickname());
        userResponse.setAvatar(userEntity.getAvatar());

        if(userPrivacySettings.isFirstNameVisible()) {
            userResponse.setFirstName(userEntity.getFirstName());
        }

        if(userPrivacySettings.isLastNameVisible()) {
            userResponse.setLastName(userEntity.getLastName());
        }

        if(userPrivacySettings.isEmailVisible()) {
            userResponse.setEmail(userEntity.getEmail());
        }

        if(userPrivacySettings.isBirthDateVisible()) {
            userResponse.setBirthDate(userEntity.getBirthDate());
        }
        return userResponse;
    }

    @Override
    public ResponseEntity<GetUserResponse> getUserProfileByNick(String userNick) {
        Optional<User> user = userRepository.findByNicknameAndStatus(userNick, Status.VERIFIED);
        User userEntity = user.orElseThrow(() -> new NotFoundException("Пользователя с таким никнеймом не существует"));

        UserPrivacySettings userPrivacySettings = userPrivacyRepository.getUserPrivacySettingsByUser(userEntity).get();

        User userResponse = setNecessaryFields(userPrivacySettings, userEntity);
        return new ResponseEntity<>(userMapper.toGetUserResponse(userResponse), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteUserAccount(User user) {
        User userEntity = userRepository.findById(user.getId()).get();
        if(userEntity.getStatus().equals(Status.DELETED)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userEntity.setStatus(Status.DELETED);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> restoreUserAccount(User user) {
        User userEntity = userRepository.findById(user.getId()).get();
        if(userEntity.getStatus().equals(Status.DELETED)) {
            userEntity.setStatus(Status.NOT_VERIFIED);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<GetUserResponse> updateUser(Long userId, UserUpdateRequest userRequest) {
        User userEntity = userMapper.toUser(userRequest);
        Optional<User> user = userRepository.findById(userId);
        User userResponse = user.orElseThrow(() -> new NotFoundException("Пользователя с таким id не существует"));
        checkUserStatus(userResponse.getStatus());
        userResponse.setBirthDate(userEntity.getBirthDate());
        userResponse.setFirstName(userEntity.getFirstName());
        userResponse.setLastName(userEntity.getLastName());
        userResponse.setNickname(userEntity.getNickname());

        return new ResponseEntity<>(userMapper.toGetUserResponse(userResponse), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserUpdateEmailResponse> updateUserEmail(Long userId, UserUpdateEmailRequest userRequest) {
        User userEntity = userMapper.toUserEntity(userRequest);
        Optional<User> user = userRepository.findById(userId);
        User userResponse = user.orElseThrow(() -> new NotFoundException("Пользователя с таким id не существует"));
        if(!userResponse.getEmail().equals(userEntity.getEmail())) {
            userResponse.setStatus(Status.NOT_VERIFIED);
            userResponse.setEmail(userEntity.getEmail());
            String jwtToken = jwtService.generateToken(userResponse);

            return new ResponseEntity<>(new UserUpdateEmailResponse(userResponse.getEmail(), jwtToken), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void checkUserStatus(Status userStatus) {
        switch(userStatus) {
            case DELETED:
                throw new NotFoundException("Вы удалили свой аккаунт");
            case BLOCKED:
                throw new NotFoundException("Ваш аккаунт заблокирован");
            case NOT_VERIFIED:
                throw new NotFoundException("Ваш аккаунт еще не подтвержден");
        }
    }


}
