//package kg.nurtelecom.opinion.repository;
//
//import kg.nurtelecom.opinion.enums.Status;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//
//import kg.nurtelecom.opinion.entity.Article;
//import kg.nurtelecom.opinion.entity.ArticleReaction;
//import kg.nurtelecom.opinion.entity.User;
//import kg.nurtelecom.opinion.enums.ReactionType;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class ArticleReactionRepositoryTest {
//
//    @Autowired
//    ArticleReactionRepository repository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    ArticleRepository articleRepository;
//
//    private Article article;
//    private User user;
//
//    @BeforeEach
//    void setUp() {
//        user = new User();
//        user.setNickname("tester");
//        user.setStatus(Status.VERIFIED);
//
//        article = new Article();
//        article.setTitle("Test Article");
//        article.setAuthor(user);
//        articleRepository.save(article);
//        userRepository.save(user);
//    }
//
//
//    @Test
//    void findByArticleAndUser_ShouldReturnArticleReaction_WhenArticleReactionExists() {
//        ArticleReaction reaction = new ArticleReaction(ReactionType.LIKE, article, user);
//        repository.save(reaction);
//
//        Optional<ArticleReaction> optionalReaction = repository.findByArticleAndUser(article, user);
//
//        assertTrue(optionalReaction.isPresent());
//        assertEquals(ReactionType.LIKE, optionalReaction.get().getReactionType());
//    }
//
//    @Test
//    void findByArticleAndUser_ShouldReturnEmptyOptional_WhenArticleReactionDoesNotExist() {
//        Optional<ArticleReaction> optionalReaction = repository.findByArticleAndUser(article, user);
//
//        assertTrue(optionalReaction.isEmpty());
//    }
//
//    @Test
//    void existsByArticleAndUser_ShouldReturnTrue_WhenArticleReactionExists() {
//        ArticleReaction reaction = new ArticleReaction(ReactionType.LIKE, article, user);
//        assertNotNull(reaction);
//    }
//
//    @Test
//    void existsByArticleAndUser_ShouldReturnFalse_WhenArticleReactionDoesNotExist() {
//        assertFalse(repository.existsByArticleAndUser(article, user));
//    }
//
//    @Test
//    void findAllByArticleIdAndReactionType_ShouldReturnPageOfArticleReactions_WhenReactionsExist() {
//        repository.save(new ArticleReaction(ReactionType.LIKE, article, user));
//        repository.save(new ArticleReaction(ReactionType.LIKE, article, user));
//        repository.save(new ArticleReaction(ReactionType.DISLIKE, article, user));
//
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<ArticleReaction> page = repository.findAllByArticleIdAndReactionType(article.getId(), ReactionType.LIKE, pageable);
//
//        assertNotNull(page);
//        assertFalse(page.isEmpty());
//        assertEquals(2, page.getTotalElements());
//    }
//
//    @Test
//    void findAllByArticleIdAndReactionType_ShouldReturnEmptyPage_WhenReactionsDoNotExist() {
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<ArticleReaction> page = repository.findAllByArticleIdAndReactionType(article.getId(), ReactionType.LIKE, pageable);
//
//        assertNotNull(page);
//        assertTrue(page.isEmpty());
//    }
//
//    @Test
//    void countByArticleIdAndReactionType_ShouldReturnCorrectCount_WhenReactionsExist() {
//
//        repository.save(new ArticleReaction(ReactionType.LIKE, article, user));
//        repository.save(new ArticleReaction(ReactionType.LIKE, article, user));
//        repository.save(new ArticleReaction(ReactionType.DISLIKE, article, user));
//
//        long count = repository.countByArticleIdAndReactionType(article.getId(), ReactionType.LIKE);
//
//        assertEquals(2, count);
//    }
//
//    @Test
//    void countByArticleIdAndReactionType_ShouldReturnZero_WhenReactionsDoNotExist() {
//        long count = repository.countByArticleIdAndReactionType(article.getId(), ReactionType.LIKE);
//        assertEquals(0, count);
//    }
//}
//
//
