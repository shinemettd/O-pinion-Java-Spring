package kg.nurtelecom.opinion.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import kg.nurtelecom.opinion.payload.user.UserSignInRequest;
import kg.nurtelecom.opinion.payload.user.UserSignInResponse;
import kg.nurtelecom.opinion.payload.user.UserSignUpRequest;
import kg.nurtelecom.opinion.payload.user.UserSignUpResponse;
import kg.nurtelecom.opinion.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    @Mock
    AuthService authService;

    @InjectMocks
    AuthController authController;


    @Test
    void signUpShouldReturnValidEntity() {
        UserSignUpResponse mockUserSignUpResponse = mock(UserSignUpResponse.class);
        UserSignUpRequest mockUserSignUpRequest = mock(UserSignUpRequest.class);
        HttpServletRequest mockHttpServletRequest = mock(HttpServletRequest.class);

        ResponseEntity<UserSignUpResponse> expectedResponse = ResponseEntity.ok(mockUserSignUpResponse);
        when(authService.signUp(mockUserSignUpRequest, mockHttpServletRequest)).thenReturn(expectedResponse);

        ResponseEntity<UserSignUpResponse> response = authController.signUp(mockUserSignUpRequest, mockHttpServletRequest);

        verify(authService,  times(1)).signUp(mockUserSignUpRequest, mockHttpServletRequest);
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void authenticateShouldReturnValidEntity() {
        UserSignInResponse mockUserSignInResponse = mock(UserSignInResponse.class);
        UserSignInRequest mockUserSignInRequest = mock(UserSignInRequest.class);

        ResponseEntity<UserSignInResponse> expectedResponse = ResponseEntity.ok(mockUserSignInResponse);
        when(authService.signIn(mockUserSignInRequest)).thenReturn(expectedResponse);

        ResponseEntity<UserSignInResponse> response = authController.authenticate(mockUserSignInRequest);

        verify(authService,  times(1)).signIn(mockUserSignInRequest);
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void verificationByEmailShouldReturnValidEntity() {
        String mockToken = "mockToken";

        ResponseEntity<String> expectedResponse = ResponseEntity.ok(mockToken);
        when(authService.checkUserVerify(mockToken)).thenReturn(expectedResponse);

        ResponseEntity<String> response = authController.verificationByEmail(mockToken);

        verify(authService, times(1)).checkUserVerify(mockToken);
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}