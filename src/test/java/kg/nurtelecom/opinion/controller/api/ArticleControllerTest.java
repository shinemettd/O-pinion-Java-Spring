package kg.nurtelecom.opinion.controller.api;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.article.ArticleGetDTO;
import kg.nurtelecom.opinion.payload.article.ArticleRequest;
import kg.nurtelecom.opinion.payload.article.ArticleResponse;
import kg.nurtelecom.opinion.payload.article.ArticlesGetDTO;
import kg.nurtelecom.opinion.payload.user.UserResponse;
import kg.nurtelecom.opinion.service.ArticleService;
import kg.nurtelecom.opinion.service.implementations.DailyVisitServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleControllerTest {
    @Mock
    ArticleService articleService;

    @InjectMocks
    ArticleController articleController;

    @Mock
    DailyVisitServiceImpl dailyVisitServiceImpl;

    @Mock
    User mockUser;

    Long mockArticleId;

    @Mock
    Pageable mockPageable;

    @BeforeEach
    public void setUp() {
        this.mockArticleId = 1L;
    }

//    @Test
//    void createArticleTestShouldReturnValidResponseEntity() {
//        ArticleRequest articleRequest = new ArticleRequest("Test title",
//                "Test description",
//                "Test content");
//        ArticleResponse expectedResponse = new ArticleResponse(1L, "Test title", "Test description", "test_path_to_img");
//        URI uri = URI.create("/articles/" + this.mockArticleId);
//        when(articleService.createArticle(eq(articleRequest), eq(this.mockUser))).thenReturn(ResponseEntity.created(uri).body(expectedResponse));
//        ResponseEntity<ArticleResponse> response = articleController.createArticle(articleRequest, this.mockUser);
//
//        verify(articleService, times(1)).createArticle(articleRequest, this.mockUser);
//        assertNotNull(response);
//        assertEquals(expectedResponse, response.getBody());
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(uri, response.getHeaders().getLocation());
//    }

    @Test
    void getArticlesTestShouldReturnValidResponseEntity() {
        Page<ArticlesGetDTO> expectedPage = mock(Page.class);
        articleController = new ArticleController(this.articleService, this.dailyVisitServiceImpl);

        when(articleService.getArticles(any(), any())).thenReturn(ResponseEntity.ok(expectedPage));

        ResponseEntity<Page<ArticlesGetDTO>> response = articleController.getArticles(this.mockPageable, this.mockUser);

        verify(articleService, times(1)).getArticles(this.mockPageable, this.mockUser);
        assertNotNull(response);
        assertEquals(ResponseEntity.ok(expectedPage), response);
        assertEquals(expectedPage, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

//    @Test
//    void getMyArticlesTestShouldReturnValidResponseEntity() {
//        Page<ArticlesGetDTO> expectedPage = mock(Page.class);
//        when(articleService.getMyArticles(eq(this.mockUser), any())).thenReturn(ResponseEntity.ok(expectedPage));
//
//        ResponseEntity<Page<ArticlesGetDTO>> response = articleController.getMyArticles(this.mockPageable, this.mockUser);
//
//        verify(articleService, times(1)).getMyArticles(this.mockUser, this.mockPageable);
//        assertNotNull(response);
//        assertEquals(ResponseEntity.ok(expectedPage), response);
//        assertEquals(expectedPage, response.getBody());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }

    @Test
    void getUserArticlesShouldReturnValidResponseEntity() {
        Page<ArticlesGetDTO> expectedPage = mock(Page.class);
        when(articleService.getUserArticles(eq(this.mockUser.getId()), any())).thenReturn(ResponseEntity.ok(expectedPage));

        ResponseEntity<Page<ArticlesGetDTO>> response = articleController.getUserArticles(this.mockUser.getId(), this.mockPageable);

        verify(articleService, times(1)).getUserArticles(this.mockUser.getId(), this.mockPageable);
        assertNotNull(response);
        assertEquals(ResponseEntity.ok(expectedPage), response);
        assertEquals(expectedPage, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

//    @Test
//    void editArticleShouldReturnValidResponseEntity() {
//        ArticleRequest mockRequest = mock(ArticleRequest.class);
//        ArticleResponse expectedResult = new ArticleResponse(
//                this.mockArticleId,
//                "Edited title",
//                "Edited description with 30 symbols as minimum",
//                "edited_img_path.jpg");
//        when(articleService.editArticle(any(), eq(this.mockArticleId), eq(this.mockUser))).thenReturn(ResponseEntity.ok(expectedResult));
//
//        ResponseEntity<ArticleResponse> response = articleController.editArticle(this.mockArticleId, mockRequest, this.mockUser);
//
//        verify(articleService, times(1)).editArticle(mockRequest, this.mockArticleId, this.mockUser);
//        assertNotNull(response);
//        assertEquals(ResponseEntity.ok(expectedResult), response);
//        assertEquals(expectedResult, response.getBody());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }

//    @Test
//    void getArticleShouldReturnValidResponseEntity() {
//        ArticleGetDTO expectedResult = new ArticleGetDTO(
//                this.mockArticleId,
//                "Title",
//                "Description with 30 symbols as minimum",
//                "img_path.jpg",
//                LocalDateTime.now(),
//                mock(UserResponse.class),
//                0L,
//                0L,
//                0L,
//                0L,
//                false,
//                "Content",
//                tags);
//        when(articleService.getArticle(eq(this.mockArticleId), eq(this.mockUser))).thenReturn(ResponseEntity.ok(expectedResult));
//
//        ResponseEntity<ArticleGetDTO> response = articleController.getArticle(this.mockArticleId, this.mockUser);
//
//        verify(articleService, times(1)).getArticle(this.mockArticleId, this.mockUser);
//        assertNotNull(response);
//        assertEquals(ResponseEntity.ok(expectedResult), response);
//        assertEquals(expectedResult, response.getBody());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }

    @Test
    void deleteArticleShouldReturnValidResponseEntity() {
        ResponseEntity<Void> expectedResult = ResponseEntity.ok().build();
        when(articleService.deleteArticle(eq(this.mockArticleId), eq(this.mockUser))).thenReturn(expectedResult);

        ResponseEntity<Void> response = articleController.deleteArticle(this.mockArticleId, this.mockUser);

        verify(articleService, times(1)).deleteArticle(this.mockArticleId, this.mockUser);
        assertNotNull(response);
        assertEquals(expectedResult, response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shareArticleShouldReturnValidResponseEntity() {
        String shareType = "share by test";
        ResponseEntity<String> expectedResult = ResponseEntity.ok(shareType);
        when(articleService.shareArticle(eq(this.mockArticleId), eq(shareType))).thenReturn(expectedResult);

        ResponseEntity<String> response = articleController.shareArticle(this.mockArticleId, shareType);

        verify(articleService, times(1)).shareArticle(this.mockArticleId, shareType);
        assertNotNull(response);
        assertEquals(expectedResult, response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void shareArticleByEmailShouldReturnValidResponseEntity() {
        String emailFrom = this.mockUser.getEmail();
        String emailTo = "test@gmail.com";
        ResponseEntity<Void> expectedResult = ResponseEntity.ok().build();
        when(articleService.shareArticleByEmail(eq(this.mockArticleId), eq(emailTo), eq(emailFrom))).thenReturn(expectedResult);

        ResponseEntity<Void> response = articleController.shareArticleByEmail(this.mockArticleId, emailTo, this.mockUser);

        verify(articleService, times(1)).shareArticleByEmail(this.mockArticleId, emailTo, emailFrom);
        assertNotNull(response);
        assertEquals(expectedResult, response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}