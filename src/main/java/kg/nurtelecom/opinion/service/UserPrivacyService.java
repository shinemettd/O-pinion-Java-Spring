package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.privacy.UserPrivacySettingsDTO;
import org.springframework.http.ResponseEntity;

public interface UserPrivacyService {
    ResponseEntity<Void> changePrivacy(UserPrivacySettingsDTO userPrivacySettings, User user);
    ResponseEntity<UserPrivacySettingsDTO> getMyPrivacySettings(User user);


}
