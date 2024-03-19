package kg.nurtelecom.opinion.controller.api;

import kg.nurtelecom.opinion.service.PasswordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordControllerTest {
    @InjectMocks
    PasswordController passwordController;

    @Mock
    PasswordService passwordService;

    @Test
    void forgotPasswordShouldReturnValidResponseEntity() {
        String mockEmail = "test@gmail.com";
        ResponseEntity<?> expectedResponse = ResponseEntity.ok().build();

        when(passwordService.requestResetToken(mockEmail)).thenReturn(ResponseEntity.ok().build());

        ResponseEntity<?> response = passwordController.forgotPassword(mockEmail);

        verify(passwordService, times(1)).requestResetToken(mockEmail);
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}