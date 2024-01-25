package kg.nurtelecom.opinion.controller.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.mapper.UserMapper;
import kg.nurtelecom.opinion.payload.user.GetUserResponse;
import kg.nurtelecom.opinion.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Tag(
        name = "Контроллер для управления пользователями "
)
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/my-profile")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<GetUserResponse> getMyProfile(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userMapper.toGetUserResponse(user), HttpStatus.OK);
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<GetUserResponse> getUserProfile(@PathVariable("id") Long userId) {
        return userService.getUserProfile(userId);
    }


}
