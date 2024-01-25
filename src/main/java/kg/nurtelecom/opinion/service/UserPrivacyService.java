package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.privacy.UserPrivacySettingsRequest;
import org.springframework.http.ResponseEntity;

public interface UserPrivacyService {
    ResponseEntity<Void> changePrivacy(UserPrivacySettingsRequest userPrivacySettings, User user);
}
