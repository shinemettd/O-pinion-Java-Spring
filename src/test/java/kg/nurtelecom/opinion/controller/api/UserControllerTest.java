package kg.nurtelecom.opinion.controller.api;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.user.*;
import kg.nurtelecom.opinion.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Mock
    User mockUser;

    @Test
    void getMyProfileShouldReturnValidResponseEntity() {
        GetUserResponse mockGetUserResponse = mock(GetUserResponse.class);
        ResponseEntity<GetUserResponse> expectedResponse = ResponseEntity.ok(mockGetUserResponse);

        when(userService.getMyProfile(this.mockUser)).thenReturn(expectedResponse);

        ResponseEntity<GetUserResponse> response = userController.getMyProfile(this.mockUser);

        verify(userService, times(1)).getMyProfile(this.mockUser);

        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getUserProfileShouldReturnValidResponseEntity() {
        Long userId = this.mockUser.getId();
        GetUserResponse mockGetUserResponse = mock(GetUserResponse.class);
        ResponseEntity<GetUserResponse> expectedResponse = ResponseEntity.ok(mockGetUserResponse);

        when(userService.getUserProfile(userId)).thenReturn(expectedResponse);

        ResponseEntity<GetUserResponse> response = userController.getUserProfile(userId);

        verify(userService, times(1)).getUserProfile(userId);

        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getUserProfileByNickShouldReturnValidResponseEntity() {
        String userNickname = this.mockUser.getNickname();
        GetUserResponse mockGetUserResponse = mock(GetUserResponse.class);
        ResponseEntity<GetUserResponse> expectedResponse = ResponseEntity.ok(mockGetUserResponse);

        when(userService.getUserProfileByNick(userNickname)).thenReturn(expectedResponse);

        ResponseEntity<GetUserResponse> response = userController.getUserProfileByNick(userNickname);

        verify(userService, times(1)).getUserProfileByNick(userNickname);

        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteUserAccountShouldReturnValidResponseEntity() {
        ResponseEntity<Void> expectedResponse = ResponseEntity.ok().build();

        when(userService.deleteUserAccount(this.mockUser)).thenReturn(expectedResponse);

        ResponseEntity<Void> response = userController.deleteUserAccount(this.mockUser);

        verify(userService, times(1)).deleteUserAccount(this.mockUser);

        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void restoreUserAccountShouldReturnValidResponseEntity() {
        ResponseEntity<Void> expectedResponse = ResponseEntity.ok().build();

        when(userService.restoreUserAccount(this.mockUser)).thenReturn(expectedResponse);

        ResponseEntity<Void> response = userController.restoreUserAccount(this.mockUser);

        verify(userService, times(1)).restoreUserAccount(this.mockUser);

        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void changeUserDataShouldReturnValidResponseEntity() {
        Long userId = this.mockUser.getId();
        UserUpdateRequest mockUserUpdateRequest = mock(UserUpdateRequest.class);
        GetUserResponse mockGetUserResponse = mock(GetUserResponse.class);
        ResponseEntity<GetUserResponse> expectedResponse = ResponseEntity.ok(mockGetUserResponse);

        when(userService.updateUser(userId, mockUserUpdateRequest)).thenReturn(expectedResponse);

        ResponseEntity<GetUserResponse> response = userController.changeUserData(this.mockUser, mockUserUpdateRequest);

        verify(userService, times(1)).updateUser(userId, mockUserUpdateRequest);

        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertSame(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void changeUserEmailShouldReturnValidResponseEntity() {
        Long userId = this.mockUser.getId();
        UserUpdateEmailRequest mockUserEmailUpdateRequest = mock(UserUpdateEmailRequest.class);
        UserUpdateEmailResponse mockUserUpdateEmailResponse = mock(UserUpdateEmailResponse.class);
        ResponseEntity<UserUpdateEmailResponse> expectedResponse = ResponseEntity.ok(mockUserUpdateEmailResponse);

        when(userService.updateUserEmail(userId, mockUserEmailUpdateRequest)).thenReturn(expectedResponse);

        ResponseEntity<UserUpdateEmailResponse> response = userController.changeUserEmail(this.mockUser, mockUserEmailUpdateRequest);

        verify(userService, times(1)).updateUserEmail(userId, mockUserEmailUpdateRequest);

        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertSame(HttpStatus.OK, response.getStatusCode());
    }
}