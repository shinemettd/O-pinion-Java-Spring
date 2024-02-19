package kg.nurtelecom.opinion.controller.api;


import kg.nurtelecom.opinion.controller.api.ArticleReactionController;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.ReactionType;
import kg.nurtelecom.opinion.payload.article_reaction.ArticleReactionRequest;
import kg.nurtelecom.opinion.payload.article_reaction.ArticleReactionResponse;
import kg.nurtelecom.opinion.payload.user.GetUserResponse;
import kg.nurtelecom.opinion.service.ArticleReactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ArticleReactionControllerTest {

    @Mock
    private ArticleReactionService articleReactionService;

    @InjectMocks
    private ArticleReactionController articleReactionController;

    private GetUserResponse user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetLikesByArticleId() {
        Page<ArticleReactionResponse> mockLikes = new PageImpl<>(Collections.singletonList(new ArticleReactionResponse(ReactionType.LIKE, user)));

        when(articleReactionService.getLikesByArticleId(any(Long.class), any(Pageable.class))).thenReturn(ResponseEntity.ok(mockLikes));

        Pageable pageable = PageRequest.of(0, 10);
        ResponseEntity<Page<ArticleReactionResponse>> responseEntity = articleReactionController.getLikesByArticleId(1L, pageable);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockLikes, responseEntity.getBody());
    }

    @Test
    public void testGetDislikesByArticleId() {
        Page<ArticleReactionResponse> mockDislikes = new PageImpl<>(Collections.singletonList(new ArticleReactionResponse(ReactionType.LIKE, user)));

        when(articleReactionService.getDislikesByArticleId(any(Long.class), any(Pageable.class))).thenReturn(ResponseEntity.ok(mockDislikes));

        Pageable pageable = PageRequest.of(0, 10); // Example page request
        ResponseEntity<Page<ArticleReactionResponse>> responseEntity = articleReactionController.getDislikesByArticleId(1L, pageable);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockDislikes, responseEntity.getBody());
    }

    @Test
    public void testAddReactionToArticle() {
        when(articleReactionService.addReactionToArticle(any(ArticleReactionRequest.class), any(User.class))).thenReturn(ResponseEntity.ok("Reaction added successfully."));

        ArticleReactionRequest request = new ArticleReactionRequest(2L, ReactionType.LIKE);
        User user = new User();
        ResponseEntity<String> responseEntity = articleReactionController.addReactionToArticle(request, user);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Reaction added successfully.", responseEntity.getBody());
    }

}

