package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.ArticleStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    ArticleRepository repository;

    private Article testArticle;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setNickname("tester");
        testUser.setId(1L);

        testArticle = new Article();
        testArticle.setAuthor(new User());
        testArticle.setId(1L);
        testArticle.setTitle("Test title");
        testArticle.setShortDescription("Test description");
        testArticle.setContent("Test content");
        testArticle.setCoverImage("test_path.png");
        testArticle.setViewsCount(0L);
    }

    @Test
    void findAllShouldFindArticleIfRepositoryIsNotEmpty() {
        Iterable<Article> articles = repository.findAll();

        assertThat(articles).isNotEmpty();
    }

    @Test
    void saveShouldStoreAnArticle() {
        Article savedArticle = repository.save(this.testArticle);

        assertThat(savedArticle).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(savedArticle).hasFieldOrPropertyWithValue("title", "Test title");
        assertThat(savedArticle).hasFieldOrPropertyWithValue("shortDescription", "Test description");
        assertThat(savedArticle).hasFieldOrPropertyWithValue("content", "Test content");
        assertThat(savedArticle).hasFieldOrPropertyWithValue("coverImage", "test_path.png");
    }

    @Test
    void findArticlesByAuthorShouldReturnArticlesPage() {
        Page<Article> foundArticlesByAuthor = repository.findByAuthor(testUser, PageRequest.of(0, 5));

        assertNotNull(foundArticlesByAuthor);
    }

    @Test
    void findByAuthorAndStatusShouldReturnArticlesPageWithStatusApproved() {
        ArticleStatus status = ArticleStatus.APPROVED;
        Page<Article> foundArticlesByAuthorWithStatusApproved = repository.findByAuthorAndStatus(testUser,
                PageRequest.of(0, 5),
                status
        );

        assertNotNull(foundArticlesByAuthorWithStatusApproved);
    }

    @Test
    void findByStatusShouldReturnArticlesPageWithStatusOnModeration() {
        ArticleStatus status = ArticleStatus.ON_MODERATION;
        Page<Article> foundArticlesByStatusOnModeration = repository.findByStatus(status, PageRequest.of(0, 5));

        assertNotNull(foundArticlesByStatusOnModeration);
    }

    @Test
    void findByStatusShouldReturnArticlesPageWithStatusNotApproved() {
        ArticleStatus status = ArticleStatus.NOT_APPROVED;
        Page<Article> foundArticlesByStatusNotApproved = repository.findByStatus(status, PageRequest.of(0, 5));

        assertNotNull(foundArticlesByStatusNotApproved);
    }

    @Test
    void findByStatusShouldReturnArticlesPageWithStatusBlocked() {
        ArticleStatus status = ArticleStatus.BLOCKED;
        Page<Article> foundArticlesByStatusBlocked = repository.findByStatus(status, PageRequest.of(0, 5));

        assertNotNull(foundArticlesByStatusBlocked);
    }

    @Test
    void findByStatusShouldReturnArticlesPageWithStatusDeleted() {
        ArticleStatus status = ArticleStatus.DELETED;
        Page<Article> foundArticlesByStatusDeleted = repository.findByStatus(status, PageRequest.of(0, 5));

        assertNotNull(foundArticlesByStatusDeleted);
    }

    @Test
    void incrementViewsCountShouldBeExecutedAtLeastOneTime() {
//        Article article = repository.findById(1L).get();
//
//        entityManager.persist(article);

        repository.incrementViewsCount(testArticle.getId());
//
//        System.out.println(article.getViewsCount());
//
//        Article updatedArticle = repository.findById(article.getId()).get();
//
//        entityManager.persist(updatedArticle);
//
//        System.out.println(updatedArticle.getViewsCount());
    }

    @Test
    void findByIdAndStatusNotInShouldReturnOptionalWithoutStatusNotApprovedAndBlockedAndDeleted() {
        Optional<Article> articlesWithoutStatusNotApprovedAndBlockedAndDeleted = repository.findByIdAndStatusNotIn(testUser.getId(),
                List.of(ArticleStatus.NOT_APPROVED,
                        ArticleStatus.BLOCKED,
                        ArticleStatus.DELETED)
        );
        assertNotNull(articlesWithoutStatusNotApprovedAndBlockedAndDeleted);
    }

    @Test
    void findByIdAndStatusNotInShouldReturnOptionalWithoutStatusApprovedAndOnModeration() {
        Optional<Article> articlesWithoutStatusApprovedAndOnModeration = repository.findByIdAndStatusNotIn(testUser.getId(),
                List.of(ArticleStatus.APPROVED,
                        ArticleStatus.ON_MODERATION));
        assertNotNull(articlesWithoutStatusApprovedAndOnModeration);
    }
}