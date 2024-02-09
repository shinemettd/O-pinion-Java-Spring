package kg.nurtelecom.opinion.controller.api;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.article.ArticleGetDTO;
import kg.nurtelecom.opinion.payload.article.ArticleRequest;
import kg.nurtelecom.opinion.payload.article.ArticleResponse;
import kg.nurtelecom.opinion.payload.article.ArticlesGetDTO;
import kg.nurtelecom.opinion.payload.user.UserResponse;
import kg.nurtelecom.opinion.service.ArticleService;
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
import org.springframework.web.multipart.MultipartFile;

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
    User mockUser;

    Long mockArticleId;

    @Mock
    MultipartFile mockContent;

    @Mock
    Pageable mockPageable;

    @BeforeEach
    public void setUp() {
        this.mockArticleId = 1L;
    }

    @Test
    void createArticleTestShouldReturnValidResponseEntity() {
        ArticleRequest articleRequest = new ArticleRequest("Test title", "Test description", "test_path_to_img");
        ArticleResponse expectedResponse = new ArticleResponse(1L, "Test title", "Test description", "test_path_to_img");
        when(articleService.createArticle(any(), eq(this.mockUser))).thenReturn(ResponseEntity.ok(expectedResponse));

        ResponseEntity<ArticleResponse> response = articleController.createArticle(articleRequest, this.mockUser);

        verify(articleService, times(1)).createArticle(articleRequest, this.mockUser);
        assertNotNull(response);
        assertEquals(expectedResponse, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void setArticleContentTestShouldReturnValidResponseEntity() {
        ResponseEntity<ArticleResponse> expectedResponse = ResponseEntity.ok(new ArticleResponse(
                                                                            mockArticleId,
                                                                        "Title",
                                                                "Description",
                                                                    "img_path.jpg"));
        when(articleService.setContent(eq(this.mockArticleId), any(), eq(this.mockUser))).thenReturn(expectedResponse);

        ResponseEntity<ArticleResponse> response = articleController.setArticleContent(this.mockArticleId, this.mockContent, this.mockUser);

        verify(articleService, times(1)).setContent(this.mockArticleId, this.mockContent, mockUser);
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void changeArticleContentTestShouldReturnValidResponseEntity() {

        ResponseEntity<ArticleResponse> expectedResponse = ResponseEntity.ok(new ArticleResponse(
                                                                        this.mockArticleId,
                                                                    "New title",
                                                                    "New description",
                                                                    "new_img_path.jpg"));
        when(articleService.setContent(eq(this.mockArticleId), any(), eq(this.mockUser))).thenReturn(expectedResponse);

        ResponseEntity<ArticleResponse> response = articleController.changeArticleContent(this.mockArticleId, this.mockContent, this.mockUser);

        verify(articleService, times(1)).setContent(this.mockArticleId, this.mockContent, this.mockUser);
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getArticlesTestShouldReturnValidResponseEntity() {
        Page<ArticlesGetDTO> expectedPage = mock(Page.class);
        when(articleService.getArticles(any(), any())).thenReturn(ResponseEntity.ok(expectedPage));

        ResponseEntity<Page<ArticlesGetDTO>> response = articleController.getArticles(this.mockPageable, this.mockUser);

        verify(articleService, times(1)).getArticles(this.mockPageable, this.mockUser);
        assertNotNull(response);
        assertEquals(ResponseEntity.ok(expectedPage), response);
        assertEquals(expectedPage, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getMyArticlesTestShouldReturnValidResponseEntity() {
        Page<ArticlesGetDTO> expectedPage = mock(Page.class);
        when(articleService.getMyArticles(eq(this.mockUser), any())).thenReturn(ResponseEntity.ok(expectedPage));

        ResponseEntity<Page<ArticlesGetDTO>> response = articleController.getMyArticles(this.mockPageable, this.mockUser);

        verify(articleService, times(1)).getMyArticles(this.mockUser, this.mockPageable);
        assertNotNull(response);
        assertEquals(ResponseEntity.ok(expectedPage), response);
        assertEquals(expectedPage, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

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

    @Test
    void editArticleShouldReturnValidResponseEntity() {
        ArticleRequest mockRequest = mock(ArticleRequest.class);
        ArticleResponse expectedResult = new ArticleResponse(
                this.mockArticleId,
                "Edited title",
                "Edited description",
                "edited_img_path.jpg");
        when(articleService.editArticle(any(), eq(this.mockArticleId), eq(this.mockUser))).thenReturn(ResponseEntity.ok(expectedResult));

        ResponseEntity<ArticleResponse> response = articleController.editArticle(this.mockArticleId, mockRequest, this.mockUser);

        verify(articleService, times(1)).editArticle(mockRequest, this.mockArticleId, this.mockUser);
        assertNotNull(response);
        assertEquals(ResponseEntity.ok(expectedResult), response);
        assertEquals(expectedResult, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getArticleShouldReturnValidResponseEntity() {
        ArticleGetDTO expectedResult = new ArticleGetDTO(
                this.mockArticleId,
                "Title",
                "Description",
                "img_path.jpg",
                LocalDateTime.now(),
                mock(UserResponse.class),
                0L,
                0L,
                0L,
                0L,
                false,
                "Content"
        );
        when(articleService.getArticle(eq(this.mockArticleId), eq(this.mockUser))).thenReturn(ResponseEntity.ok(expectedResult));

        ResponseEntity<ArticleGetDTO> response = articleController.getArticle(this.mockArticleId, this.mockUser);

        verify(articleService, times(1)).getArticle(this.mockArticleId, this.mockUser);
        assertNotNull(response);
        assertEquals(ResponseEntity.ok(expectedResult), response);
        assertEquals(expectedResult, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

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