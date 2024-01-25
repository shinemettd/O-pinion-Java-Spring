package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.entity.UserPrivacySettings;
import kg.nurtelecom.opinion.payload.privacy.UserPrivacySettingsRequest;
import kg.nurtelecom.opinion.repository.UserPrivacyRepository;
import kg.nurtelecom.opinion.service.UserPrivacyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserPrivacyServiceImpl implements UserPrivacyService {
    private final UserPrivacyRepository userPrivacyRepository;

    public UserPrivacyServiceImpl(UserPrivacyRepository userPrivacyRepository) {
        this.userPrivacyRepository = userPrivacyRepository;
    }

    @Override
    public ResponseEntity<Void> changePrivacy(UserPrivacySettingsRequest userPrivacySettingsRequest, User user) {
        UserPrivacySettings userPrivacy = userPrivacyRepository.getUserPrivacySettingsByUser(user).get();
        userPrivacy.setFirstNameVisible(userPrivacySettingsRequest.isFirstNameVisible());
        userPrivacy.setLastNameVisible(userPrivacySettingsRequest.isLastNameVisible());
        userPrivacy.setEmailVisible(userPrivacySettingsRequest.isEmailVisible());
        userPrivacy.setBirthDateVisible(userPrivacySettingsRequest.isBirthDateVisible());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
