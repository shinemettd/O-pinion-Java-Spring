//package kg.nurtelecom.opinion.repository;
//
//import kg.nurtelecom.opinion.entity.ConfirmationToken;
//import kg.nurtelecom.opinion.entity.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class ConfirmationTokenRepositoryTest {
//
//    private final String MOCK_TOKEN = "testMockToken";
//
//    @Autowired
//    ConfirmationTokenRepository repository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    private ConfirmationToken token;
//
//    private User testUser;
//
//    @BeforeEach
//    void setUp() {
//        testUser = new User();
//        testUser.setNickname("tester");
//        userRepository.save(testUser);
//
//        token = repository.save(new ConfirmationToken(MOCK_TOKEN, testUser));
//    }
//
//    @Test
//    void findByTokenWithValidTokenValueShouldReturnConfirmationToken() {
//        Optional<ConfirmationToken> findByTokenResult = repository.findByToken(MOCK_TOKEN);
//
//        assertThat(findByTokenResult).isNotEmpty();
//
//        ConfirmationToken foundToken = findByTokenResult.get();
//        assertEquals(this.testUser, foundToken.getUser());
//        assertEquals(MOCK_TOKEN, foundToken.getToken());
//    }
//
//    @Test
//    void findByTokenWithInvalidTokenValueShouldReturnEmptyOptional() {
//        Optional<ConfirmationToken> findByTokenResult = repository.findByToken("Invalid token value");
//
//        assertThat(findByTokenResult).isEmpty();
//    }
//}