package kg.nurtelecom.opinion.controller.api;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.article_comment.ArticleCommentRequest;
import kg.nurtelecom.opinion.payload.article_comment.ArticleCommentResponse;
import kg.nurtelecom.opinion.payload.article_comment.ArticleReplyCommentResponse;
import kg.nurtelecom.opinion.payload.user.UserResponse;
import kg.nurtelecom.opinion.service.ArticleCommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class ArticleCommentControllerTest {

    @Mock
    private ArticleCommentService commentService;

    @InjectMocks
    private ArticleCommentController commentController;

    private User mockUser;

    private Pageable mockPageable;

    private UserResponse userResponse;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockUser = new User();
        ArticleCommentRequest articleCommentRequest = new ArticleCommentRequest("Test comment");

        mockPageable = PageRequest.of(0, 10);
    }

    @Test
    public void testGetRootComments() {

        Integer mockReplies = 2;

        Long commentId = 1L;
        String commentText = "Example comment text";
        LocalDateTime commentDate = LocalDateTime.now();
        boolean isAltered = false;
        int commentDepth = 0;

        ArticleCommentResponse articleCommentResponse = new ArticleCommentResponse(
                commentId,
                commentText,
                commentDate,
                isAltered,
                userResponse,
                commentDepth,
                mockReplies
        );

        Page<ArticleCommentResponse> commentResponsePage = new PageImpl<>(Collections.singletonList(articleCommentResponse));

        when(commentService.getRootComments(any(Long.class), any(Pageable.class))).thenReturn(ResponseEntity.ok(commentResponsePage));

        ResponseEntity<Page<ArticleCommentResponse>> response = commentController.getRootComments(1L, mockPageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(commentResponsePage);

    }

    @Test
    public void testGetCommentReplies() {
        List<ArticleReplyCommentResponse> replies = Arrays.asList(
                new ArticleReplyCommentResponse(1L, "Reply 1", LocalDateTime.now(), false, userResponse, 0),
                new ArticleReplyCommentResponse(2L, "Reply 2", LocalDateTime.now(), false, userResponse, 0)
        );
        Page<ArticleReplyCommentResponse> replyPage = new PageImpl<>(replies);

        when(commentService.getCommentReplies(any(Long.class), any(Pageable.class)))
                .thenReturn(ResponseEntity.ok(replyPage));


        ResponseEntity<Page<ArticleReplyCommentResponse>> response =
                commentController.getCommentReplies(1L, mockPageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(replies.size(), response.getBody().getContent().size());
    }


    @Test
    public void testUpdateCommentById() {
        Integer mockReplies = 2;
        Long commentId = 2L;
        String commentText = "Some text";
        LocalDateTime commentDate = LocalDateTime.now();
        boolean isAltered = false;
        int commentDepth = 0;

        ArticleCommentResponse articleCommentResponse = new ArticleCommentResponse(
                commentId,
                commentText,
                commentDate,
                isAltered,
                userResponse,
                commentDepth,
                mockReplies
        );

        when(commentService.updateCommentById(any(Long.class),
                any(ArticleCommentRequest.class),
                any(User.class)))
                .thenReturn(ResponseEntity.ok(articleCommentResponse));


        ResponseEntity<ArticleCommentResponse> response =
                commentController.
                        updateCommentById(2L, new ArticleCommentRequest("Updated text"), mockUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }


    @Test
    public void testDeleteCommentById() {
        Long commentId = 2L;

        when(commentService.deleteCommentById(any(Long.class), any(User.class))).thenReturn(ResponseEntity.ok().build());

        ResponseEntity<Void> response = commentController.deleteCommentById(commentId, mockUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
