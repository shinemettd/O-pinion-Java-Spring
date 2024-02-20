package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.PasswordResetToken;
import kg.nurtelecom.opinion.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PasswordResetTokenRepositoryTest {

    private final String MOCK_TOKEN = "testMockToken";

    @Autowired
    PasswordResetTokenRepository repository;

    @Autowired
    UserRepository userRepository;

    private PasswordResetToken token;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setNickname("tester");
        userRepository.save(testUser);
        token = repository.save(new PasswordResetToken(MOCK_TOKEN, testUser));
    }

    @Test
    void findByTokenWithValidTokenValueShouldReturnPasswordResetToken() {
        Optional<PasswordResetToken> findByTokenResult = repository.findByToken(MOCK_TOKEN);

        assertThat(findByTokenResult).isNotEmpty();

        PasswordResetToken foundToken = findByTokenResult.get();
        assertEquals(this.testUser, foundToken.getUser());
        assertEquals(MOCK_TOKEN, foundToken.getToken());
    }

    @Test
    void findByTokenWithInvalidTokenValueShouldReturnEmptyOptional() {
        Optional<PasswordResetToken> findByTokenResult = repository.findByToken("Invalid token value");

        assertThat(findByTokenResult).isEmpty();
    }

    @Test
    void findByUserWithValidUserShouldReturnPasswordResetToken() {
        Optional<PasswordResetToken> findByUserResult = repository.findByUser(testUser);

        assertThat(findByUserResult).isNotEmpty();

        PasswordResetToken foundToken = findByUserResult.get();
        assertEquals(this.testUser, foundToken.getUser());
        assertEquals(MOCK_TOKEN, foundToken.getToken());
    }

    @Test
    void findByUserWithInvalidUserShouldReturnEmptyOptional() {
        User invalidUser = new User();
        invalidUser.setId(999L);

        Optional<PasswordResetToken> findByUserResult = repository.findByUser(invalidUser);

        assertThat(findByUserResult).isEmpty();
    }
}
