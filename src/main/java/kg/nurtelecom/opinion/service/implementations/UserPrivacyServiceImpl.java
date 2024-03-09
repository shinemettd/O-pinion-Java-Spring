package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.entity.UserPrivacySettings;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.payload.privacy.UserPrivacySettingsDTO;
import kg.nurtelecom.opinion.repository.UserPrivacyRepository;
import kg.nurtelecom.opinion.service.UserPrivacyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserPrivacyServiceImpl implements UserPrivacyService {
    private final UserPrivacyRepository userPrivacyRepository;


    public UserPrivacyServiceImpl(UserPrivacyRepository userPrivacyRepository) {
        this.userPrivacyRepository = userPrivacyRepository;

    }

    @Override
    public ResponseEntity<Void> changePrivacy(UserPrivacySettingsDTO userPrivacySettingsDTO, User user) {
        Optional<UserPrivacySettings> userPrivacy = userPrivacyRepository.getUserPrivacySettingsByUser(user);
        UserPrivacySettings userPrivacyEntity = userPrivacy.orElseThrow(() -> new NotFoundException("У вас отсутствуют настройки приватности , создайте свой аккаунт через приложение "));
        userPrivacyEntity.setFirstNameVisible(userPrivacySettingsDTO.isFirstNameVisible());
        userPrivacyEntity.setLastNameVisible(userPrivacySettingsDTO.isLastNameVisible());
        userPrivacyEntity.setEmailVisible(userPrivacySettingsDTO.isEmailVisible());
        userPrivacyEntity.setBirthDateVisible(userPrivacySettingsDTO.isBirthDateVisible());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserPrivacySettingsDTO> getMyPrivacySettings(User user) {
        Optional<UserPrivacySettings> userPrivacy = userPrivacyRepository.getUserPrivacySettingsByUser(user);
        UserPrivacySettings userPrivacyEntity = userPrivacy.orElseThrow(() -> new NotFoundException("У вас отсутствуют настройки приватности , создайте свой аккаунт через приложение "));
        UserPrivacySettingsDTO response = new UserPrivacySettingsDTO(
                userPrivacyEntity.isFirstNameVisible(),
                userPrivacyEntity.isLastNameVisible(),
                userPrivacyEntity.isEmailVisible(),
                userPrivacyEntity.isBirthDateVisible()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
