package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.mapper.UserMapper;
import kg.nurtelecom.opinion.payload.user.GetUserResponse;
import kg.nurtelecom.opinion.repository.UserRepository;
import kg.nurtelecom.opinion.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseEntity<GetUserResponse> getUserProfile(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new NotFoundException("Пользователя с таким id не существует");
        }
        return new ResponseEntity<>(userMapper.toGetUserResponse(user.get()), HttpStatus.OK);
    }
}
