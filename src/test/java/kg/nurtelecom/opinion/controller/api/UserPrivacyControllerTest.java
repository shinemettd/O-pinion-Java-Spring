package kg.nurtelecom.opinion.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.privacy.UserPrivacySettingsDTO;
import kg.nurtelecom.opinion.service.UserPrivacyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserPrivacyControllerTest {

    @Mock
    private UserPrivacyService userPrivacyService;

    @InjectMocks
    private UserPrivacyController userPrivacyController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testChangeUserPrivacy() throws Exception {

        User user = new User();
        UserPrivacySettingsDTO privacySettingsRequest = new UserPrivacySettingsDTO(true, true, true, true);

        ResponseEntity<Void> expectedResponse = ResponseEntity.ok().build();
        when(userPrivacyService.changePrivacy(any(UserPrivacySettingsDTO.class), any(User.class))).thenReturn(expectedResponse);

        ResponseEntity<Void> responseEntity = userPrivacyController.changeUserPrivacy(privacySettingsRequest, user);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(privacySettingsRequest);
        assertEquals(requestBody, objectMapper.writeValueAsString(privacySettingsRequest));
    }
}

