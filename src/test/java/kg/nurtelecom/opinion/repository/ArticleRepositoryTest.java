//package kg.nurtelecom.opinion.repository;
//
//import kg.nurtelecom.opinion.entity.Article;
//import kg.nurtelecom.opinion.entity.User;
//import kg.nurtelecom.opinion.enums.ArticleStatus;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class ArticleRepositoryTest {
//
//    @Autowired
//    ArticleRepository repository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    private Article testArticle;
//
//    private User testUser;
//
//    @BeforeEach
//    void setUp() {
//        testUser = new User();
//        testUser.setNickname("tester");
//        testUser = userRepository.save(testUser);
//
//        testArticle = new Article();
//        testArticle.setAuthor(testUser);
//        testArticle.setTitle("Test title");
//        testArticle.setShortDescription("Test description");
//        testArticle.setContent("Test content");
//        testArticle.setCoverImage("test_path.png");
//        testArticle.setViewsCount(0L);
//
//        testArticle = repository.save(testArticle);
//    }
//
//    @Test
//    void findAllShouldFindArticleIfRepositoryIsNotEmpty() {
//        Iterable<Article> articles = repository.findAll();
//
//        assertThat(articles).isNotEmpty();
//    }
//
//    @Test
//    void saveShouldStoreAnArticle() {
//        Article savedArticle = repository.save(this.testArticle);
//
//        assertThat(savedArticle).hasFieldOrPropertyWithValue("id", savedArticle.getId());
//        assertThat(savedArticle).hasFieldOrPropertyWithValue("title", "Test title");
//        assertThat(savedArticle).hasFieldOrPropertyWithValue("shortDescription", "Test description");
//        assertThat(savedArticle).hasFieldOrPropertyWithValue("content", "Test content");
//        assertThat(savedArticle).hasFieldOrPropertyWithValue("coverImage", "test_path.png");
//    }
//
//    @Test
//    void findArticlesByAuthorShouldReturnArticlesPage() {
//        Page<Article> foundArticlesByAuthor = repository.findByAuthor(testUser, PageRequest.of(0, 1));
//
//        assertThat(foundArticlesByAuthor).isNotEmpty();
//    }
//
//    @Test
//    void findByAuthorAndStatusShouldReturnNotEmptyArticlesPageWithStatusApproved() {
//        ArticleStatus status = ArticleStatus.APPROVED;
//
//        Article articleWithStatusApproved = new Article();
//        articleWithStatusApproved.setAuthor(testUser);
//        articleWithStatusApproved.setStatus(status);
//
//        Article a = repository.save(articleWithStatusApproved);
//
//        Page<Article> foundArticlesByAuthorWithStatusApproved = repository.findByAuthorAndStatus(testUser,
//                PageRequest.of(0, 1),
//                status
//        );
//
//        assertThat(foundArticlesByAuthorWithStatusApproved).isNotEmpty();
//        foundArticlesByAuthorWithStatusApproved
//                .stream()
//                .forEach(
//                        article -> assertEquals(ArticleStatus.APPROVED, article.getStatus())
//                );
//    }
//
//    @Test
//    void findByStatusShouldReturnArticlesPageWithStatusOnModeration() {
//        ArticleStatus status = ArticleStatus.ON_MODERATION;
//
//        Article articleWithStatusApproved = new Article();
//        articleWithStatusApproved.setStatus(status);
//
//        Page<Article> foundArticlesByStatusOnModeration = repository.findByStatus(status, PageRequest.of(0, 1));
//
//        assertThat(foundArticlesByStatusOnModeration).isNotEmpty();
//        foundArticlesByStatusOnModeration
//                .stream()
//                .forEach(
//                        article -> assertEquals(ArticleStatus.ON_MODERATION, article.getStatus())
//                );
//    }
//
//    @Test
//    void findByStatusShouldReturnArticlesPageWithStatusNotApproved() {
//        ArticleStatus status = ArticleStatus.NOT_APPROVED;
//
//        Article articleWithStatusApproved = new Article();
//        articleWithStatusApproved.setStatus(status);
//
//        Page<Article> foundArticlesByStatusNotApproved = repository.findByStatus(status, PageRequest.of(0, 1));
//
//        assertThat(foundArticlesByStatusNotApproved).isNotEmpty();
//        foundArticlesByStatusNotApproved
//                .stream()
//                .forEach(
//                        article -> assertEquals(ArticleStatus.NOT_APPROVED, article.getStatus())
//                );
//    }
//
//    @Test
//    void findByStatusShouldReturnArticlesPageWithStatusBlocked() {
//        ArticleStatus status = ArticleStatus.BLOCKED;
//
//        Article articleWithStatusApproved = new Article();
//        articleWithStatusApproved.setStatus(status);
//
//        Page<Article> foundArticlesByStatusBlocked = repository.findByStatus(status, PageRequest.of(0, 1));
//
//        assertThat(foundArticlesByStatusBlocked).isNotEmpty();
//        foundArticlesByStatusBlocked
//                .stream()
//                .forEach(
//                        article -> assertEquals(ArticleStatus.BLOCKED, article.getStatus())
//                );
//    }
//
//    @Test
//    void findByStatusShouldReturnArticlesPageWithStatusDeleted() {
//        ArticleStatus status = ArticleStatus.DELETED;
//
//        Article articleWithStatusApproved = new Article();
//        articleWithStatusApproved.setStatus(status);
//
//        Page<Article> foundArticlesByStatusDeleted = repository.findByStatus(status, PageRequest.of(0, 1));
//
//        assertThat(foundArticlesByStatusDeleted).isNotNull();
//        foundArticlesByStatusDeleted
//                .stream()
//                .forEach(
//                        article -> assertEquals(ArticleStatus.DELETED, article.getStatus())
//                );
//    }
//
//    @Test
//    void findByIdAndStatusNotInWithoutStatusNotApprovedAndBlockedAndDeletedShouldWithApprovedReturnArticle() {
//        Article articleWithStatusApproved = new Article();
//        articleWithStatusApproved.setStatus(ArticleStatus.APPROVED);
//        articleWithStatusApproved.setAuthor(this.testUser);
//        articleWithStatusApproved = repository.save(articleWithStatusApproved);
//
//        Optional<Article> returnedArticleWithoutStatusNotApprovedAndBlockedAndDeleted = repository.findByIdAndStatusNotIn(
//                articleWithStatusApproved.getId(),
//                List.of(ArticleStatus.NOT_APPROVED,
//                        ArticleStatus.BLOCKED,
//                        ArticleStatus.DELETED)
//        );
//
//        assertThat(returnedArticleWithoutStatusNotApprovedAndBlockedAndDeleted).isNotEmpty();
//        assertThat(returnedArticleWithoutStatusNotApprovedAndBlockedAndDeleted.get()).
//                extracting(Article::getStatus)
//                .isNotIn(ArticleStatus.NOT_APPROVED,
//                        ArticleStatus.BLOCKED,
//                        ArticleStatus.DELETED);
//    }
//
//    @Test
//    void findByIdAndStatusNotInWithoutStatusNotApprovedAndBlockedAndDeletedWithOnModerationShouldReturnArticle() {
//        Article articleWithStatusOnModeration = new Article();
//        articleWithStatusOnModeration.setStatus(ArticleStatus.ON_MODERATION);
//        articleWithStatusOnModeration.setAuthor(this.testUser);
//        articleWithStatusOnModeration = repository.save(articleWithStatusOnModeration);
//
//        Optional<Article> returnedArticleWithoutStatusNotApprovedAndBlockedAndDeleted = repository.findByIdAndStatusNotIn(
//                articleWithStatusOnModeration.getId(),
//                List.of(ArticleStatus.NOT_APPROVED,
//                        ArticleStatus.BLOCKED,
//                        ArticleStatus.DELETED)
//        );
//
//        assertThat(returnedArticleWithoutStatusNotApprovedAndBlockedAndDeleted).isNotEmpty();
//        assertThat(returnedArticleWithoutStatusNotApprovedAndBlockedAndDeleted.get()).
//                extracting(Article::getStatus)
//                .isNotIn(ArticleStatus.NOT_APPROVED,
//                        ArticleStatus.BLOCKED,
//                        ArticleStatus.DELETED);
//    }
//
//    @Test
//    void findByIdAndStatusNotInWithoutStatusNotApprovedAndBlockedAndDeletedWithNotApprovedShouldReturnEmptyOptional() {
//        Article articleWithStatusNotApproved = new Article();
//        articleWithStatusNotApproved.setStatus(ArticleStatus.NOT_APPROVED);
//        articleWithStatusNotApproved.setAuthor(this.testUser);
//        articleWithStatusNotApproved = repository.save(articleWithStatusNotApproved);
//
//        Optional<Article> returnedArticleWithoutStatusNotApprovedAndBlockedAndDeleted = repository.findByIdAndStatusNotIn(
//                articleWithStatusNotApproved.getId(),
//                List.of(ArticleStatus.NOT_APPROVED,
//                        ArticleStatus.BLOCKED,
//                        ArticleStatus.DELETED)
//        );
//
//        assertThat(returnedArticleWithoutStatusNotApprovedAndBlockedAndDeleted).isEmpty();
//    }
//
//    @Test
//    void findByIdAndStatusNotInWithoutStatusNotApprovedAndBlockedAndDeletedWithStatusBlockedShouldReturnEmptyOptional() {
//        Article articleWithStatusBlocked = new Article();
//        articleWithStatusBlocked.setStatus(ArticleStatus.BLOCKED);
//        articleWithStatusBlocked.setAuthor(this.testUser);
//        articleWithStatusBlocked = repository.save(articleWithStatusBlocked);
//
//        Optional<Article> returnedArticleWithStatusBlocked = repository.findByIdAndStatusNotIn(
//                articleWithStatusBlocked.getId(),
//                List.of(ArticleStatus.NOT_APPROVED,
//                        ArticleStatus.BLOCKED,
//                        ArticleStatus.DELETED)
//        );
//
//        assertThat(returnedArticleWithStatusBlocked).isEmpty();
//    }
//
//    @Test
//    void findByIdAndStatusNotInWithoutStatusNotApprovedAndBlockedAndDeletedWithStatusDeletedShouldReturnEmptyOptional() {
//        Article articleWithStatusDeleted = new Article();
//        articleWithStatusDeleted.setStatus(ArticleStatus.DELETED);
//        articleWithStatusDeleted.setAuthor(this.testUser);
//        articleWithStatusDeleted = repository.save(articleWithStatusDeleted);
//
//        Optional<Article> returnedArticleWithStatusDeleted = repository.findByIdAndStatusNotIn(
//                articleWithStatusDeleted.getId(),
//                List.of(ArticleStatus.NOT_APPROVED,
//                        ArticleStatus.BLOCKED,
//                        ArticleStatus.DELETED)
//        );
//
//        assertThat(returnedArticleWithStatusDeleted).isEmpty();
//    }
//}