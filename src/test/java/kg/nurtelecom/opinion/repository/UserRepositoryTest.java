package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.entity.UserPrivacySettings;
import kg.nurtelecom.opinion.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    private UserPrivacySettings settings;

    private User testUser;


    @BeforeEach
    void setUp() {
        settings = new UserPrivacySettings();
        settings.setEmailVisible(true);
        settings.setFirstNameVisible(true);
        settings.setBirthDateVisible(true);
        settings.setLastNameVisible(true);
        settings.setBirthDateVisible(true);

        testUser = new User();
        testUser.setNickname("tester");
        testUser.setPrivacySettings(settings);
        testUser.setEmail("test@example.com");
        testUser.setStatus(Status.VERIFIED);

        testUser = userRepository.save(testUser);
    }

    @Test
    public void testFindByEmail() {

        userRepository.save(testUser);

        Optional<User> foundUser = userRepository.findByEmail("test@example.com");

        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertEquals("test@example.com", foundUser.get().getEmail());
    }

    @Test
    public void testExistsByEmailOrNickname() {

        boolean existsAfterSave = userRepository.existsByEmailOrNickname("test@example.com", "tester");

        Assertions.assertTrue(existsAfterSave);
    }


    @Test
    public void testFindByIdAndStatus() {

        Optional<User> foundUser = userRepository.findByIdAndStatus(testUser.getId(), Status.VERIFIED);
        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertEquals(testUser.getId(), foundUser.get().getId());
    }

    @Test
    public void testFindByNicknameAndStatus() {

        Optional<User> foundUser = userRepository.findByNicknameAndStatus("tester", Status.VERIFIED);
        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertEquals("tester", foundUser.get().getNickname());
    }
}

