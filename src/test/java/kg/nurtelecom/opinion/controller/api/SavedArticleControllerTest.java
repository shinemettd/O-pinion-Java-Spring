package kg.nurtelecom.opinion.controller.api;


import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.saved_article.SavedArticleResponse;
import kg.nurtelecom.opinion.service.SavedArticlesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Objects;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class SavedArticleControllerTest {

    @Mock
    private SavedArticlesService savedArticlesService;

    @InjectMocks
    private SavedArticleController savedArticleController;

    private User mockUser;

    private Pageable mockPageable;

    private Long articleId = 123L;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockUser = new User();

        mockPageable = PageRequest.of(0, 10);
    }

    @Test
    public void testGetSavedArticles() {

        Page<SavedArticleResponse> savedArticleResponsePage = new PageImpl<>(Collections.emptyList());

        when(savedArticlesService.getSavedArticles(any(User.class), any(Pageable.class)))
                .thenReturn(ResponseEntity.ok(savedArticleResponsePage));


        ResponseEntity<Page<SavedArticleResponse>> responseEntity =
                savedArticleController.getSavedArticles(mockPageable, mockUser);


        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertNotNull(Objects.requireNonNull(responseEntity.getBody()));

    }

    @Test
    public void testAddArticleToSaved() {

        when(savedArticlesService.addArticleToSaved(articleId, mockUser))
                .thenReturn(ResponseEntity.ok().build());

        ResponseEntity<Void> responseEntity =
                savedArticleController.addArticleToSaved(articleId, mockUser);


        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteArticleFromSaved() {

        when(savedArticlesService.deleteArticleFromSaved(articleId, mockUser))
                .thenReturn(ResponseEntity.ok().build());


        ResponseEntity<Void> responseEntity =
                savedArticleController.deleteArticleFromSaved(articleId, mockUser);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
