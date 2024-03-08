package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.ArticleComment;
import kg.nurtelecom.opinion.entity.UserPrivacySettings;
import kg.nurtelecom.opinion.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import kg.nurtelecom.opinion.entity.User;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ArticleCommentRepositoryTest {

    @Autowired
    private ArticleCommentRepository articleCommentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    private Article testArticle;
    @Mock
    private User testUser;

    private UserPrivacySettings userPrivacySettings;

    @BeforeEach
    void setUp() {
        testUser.setNickname("tester");
        testUser.setPrivacySettings(userPrivacySettings);
        testUser.setEmail("test@example.com");
        testUser.setStatus(Status.VERIFIED);

        testArticle = new Article();
        testArticle.setAuthor(testUser);
        testArticle.setTitle("Test title");
        testArticle.setShortDescription("Test description");
        testArticle.setContent("Test content");
        testArticle.setCoverImage("test_path.png");
        testArticle.setViewsCount(0L);
        userRepository.save(testUser);

        testArticle = articleRepository.save(testArticle);
    }

//    @Test
//    public void testFindRootComments() {
//
//        ArticleComment rootComment1 = createArticleComment("Корневой комментарий 1", null);
//        ArticleComment rootComment2 = createArticleComment("Корневой комментарий 2", null);
//        ArticleComment childComment = createArticleComment("Дочерний комментарий", rootComment1);
//
//        articleCommentRepository.save(rootComment1);
//        articleCommentRepository.save(rootComment2);
//        articleCommentRepository.save(childComment);
//
//        Page<ArticleComment> rootComments = articleCommentRepository.findRootComments(testArticle.getId(), PageRequest.of(0, 10));
//
//        Assertions.assertEquals(2, rootComments.getTotalElements());
//    }
//
//    @Test
//    public void testFindCommentReplies() {
//        ArticleComment rootComment = createArticleComment("Root comment", null);
//        ArticleComment replyComment1 = createArticleComment("Reply comment 1", rootComment);
//        ArticleComment replyComment2 = createArticleComment("Reply comment 2", rootComment);
//
//        articleCommentRepository.save(rootComment);
//        articleCommentRepository.save(replyComment1);
//        articleCommentRepository.save(replyComment2);
//
//        Page<ArticleComment> commentReplies = articleCommentRepository.findCommentReplies(rootComment.getId(), PageRequest.of(0, 10));
//
//        Assertions.assertEquals(2, commentReplies.getTotalElements());
//    }

    private ArticleComment createArticleComment(String text, ArticleComment parentComment) {
        ArticleComment articleComment = new ArticleComment();
        articleComment.setText(text);
        articleComment.setDate(LocalDateTime.now());
        articleComment.setAltered(false);
        articleComment.setDepth(parentComment == null ? 0 : parentComment.getDepth() + 1);
        articleComment.setParentComment(parentComment);
        articleComment.setArticle(testArticle);
        return articleComment;
    }
}
