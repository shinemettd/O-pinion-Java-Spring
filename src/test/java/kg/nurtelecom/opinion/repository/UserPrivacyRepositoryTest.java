//package kg.nurtelecom.opinion.repository;
//
//import kg.nurtelecom.opinion.entity.User;
//import kg.nurtelecom.opinion.entity.UserPrivacySettings;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class UserPrivacyRepositoryTest {
//
//    @Autowired
//    UserPrivacyRepository repository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    private User testUser;
//
//    private UserPrivacySettings settings;
//
//    @BeforeEach
//    void setUp() {
//        settings = new UserPrivacySettings();
//        settings.setEmailVisible(true);
//        settings.setFirstNameVisible(true);
//        settings.setBirthDateVisible(false);
//        settings.setLastNameVisible(false);
//        settings.setBirthDateVisible(true);
//
//        testUser = new User();
//        testUser.setNickname("tester");
//        testUser.setPrivacySettings(settings);
//
//        testUser = userRepository.save(testUser);
//    }
//
//    @Test
//    void getUserPrivacySettingsByExistingUserShouldReturnValidValue() {
//        UserPrivacySettings validPrivacySettings = (repository.getUserPrivacySettingsByUser(testUser)).get();
//
//        assertThat(validPrivacySettings).isNotNull();
//        assertThat(validPrivacySettings).hasFieldOrPropertyWithValue("emailVisible", settings.isEmailVisible());
//        assertThat(validPrivacySettings).hasFieldOrPropertyWithValue("firstNameVisible", settings.isFirstNameVisible());
//        assertThat(validPrivacySettings).hasFieldOrPropertyWithValue("lastNameVisible", settings.isLastNameVisible());
//        assertThat(validPrivacySettings).hasFieldOrPropertyWithValue("birthDateVisible", settings.isBirthDateVisible());
//    }
//
//    @Test
//    void getUserPrivacySettingsByNonExistingUserShouldReturnEmptyOptional() {
//        User nonExistingUser = new User();
//        nonExistingUser.setId(Long.MAX_VALUE);
//
//        Optional<UserPrivacySettings> validPrivacySettings = repository.getUserPrivacySettingsByUser(nonExistingUser);
//        assertThat(validPrivacySettings).isEmpty();
//    }
//}