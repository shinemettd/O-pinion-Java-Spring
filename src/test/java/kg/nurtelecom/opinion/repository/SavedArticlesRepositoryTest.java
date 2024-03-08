//package kg.nurtelecom.opinion.repository;
//
//import kg.nurtelecom.opinion.entity.Article;
//import kg.nurtelecom.opinion.entity.SavedArticle;
//import kg.nurtelecom.opinion.entity.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class SavedArticlesRepositoryTest {
//
//    @Autowired
//    SavedArticlesRepository repository;
//
//    private SavedArticle testSavedArticle;
//
//    private Article testArticle;
//
//    private User testUser;
//
//    @BeforeEach
//    void setUp() {
//        testUser = new User();
//        testUser.setNickname("tester");
//        testUser.setId(1L);
//
//        testArticle = new Article();
//        testArticle.setAuthor(testUser);
//        testArticle.setId(1L);
//        testArticle.setTitle("Test title");
//        testArticle.setShortDescription("Test description");
//        testArticle.setContent("Test content");
//        testArticle.setCoverImage("test_path.png");
//        testArticle.setViewsCount(0L);
//
//        testSavedArticle = new SavedArticle(testArticle, testUser);
//        repository.save(testSavedArticle);
//    }
//
//    @Test
//    void findByArticleIdAndUserIdWithValidValuesShouldReturnValidSavedArticle() {
//        Long articleId = testArticle.getId();
//        Long userId = testUser.getId();
//
//        SavedArticle foundSavedArticle = (repository.findByArticleIdAndUserId(articleId, userId)).get();
//
//        assertThat(foundSavedArticle).isNotNull();
//        assertThat(foundSavedArticle).hasFieldOrPropertyWithValue("article", testArticle);
//        assertThat(foundSavedArticle).hasFieldOrPropertyWithValue("user", testUser);
//    }
//
//    @Test
//    void findByArticleIdAndUserIdWithWrongValuesShouldReturnEmptyOptional() {
//        Long wrongArticleId = testArticle.getId() + 1;
//        Long wrongUserId = testUser.getId() + 1;
//
//        Optional<SavedArticle> notFoundSavedArticle = (repository.findByArticleIdAndUserId(wrongArticleId, wrongUserId));
//
//        assertThat(notFoundSavedArticle).isEmpty();
//    }
//
//    @Test
//    void findAllByUserWithValidUserShouldReturnNotEmptyPage() {
//        Page<SavedArticle> foundSavedArticles = repository.findAllByUser(testUser, PageRequest.of(0, 5));
//
//        assertThat(foundSavedArticles).isNotEmpty();
//    }
//
//    @Test
//    void findAllByUserWithWrongUserShouldReturnEmptyPage() {
//        User wrongUser = new User();
//        Long wrongUserId = testUser.getId() + 1;
//        wrongUser.setId(wrongUserId);
//
//        Page<SavedArticle> emptySavedArticles = repository.findAllByUser(wrongUser, PageRequest.of(0, 5));
//
//        assertThat(emptySavedArticles).isEmpty();
//    }
//
//    @Test
//    void countByArticleIdShouldBeGreaterThanZero() {
//        Long articleId = testArticle.getId();
//
//        Long articleInSavedCount = repository.countByArticleId(articleId);
//
//        assertThat(articleInSavedCount).isGreaterThan(0);
//    }
//}