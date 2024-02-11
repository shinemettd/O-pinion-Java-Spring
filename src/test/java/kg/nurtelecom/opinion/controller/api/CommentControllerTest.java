package kg.nurtelecom.opinion.controller.api_tests;

import kg.nurtelecom.opinion.controller.api.CommentController;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.comment.CommentRequest;
import kg.nurtelecom.opinion.payload.comment.CommentResponse;
import kg.nurtelecom.opinion.payload.user.UserResponse;
import kg.nurtelecom.opinion.service.CommentService;
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
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private User mockUser;

    private CommentRequest mockCommentRequest;

    private Pageable mockPageable;

    private CommentResponse commentResponse;

    private UserResponse userResponse;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockUser = new User();
        mockCommentRequest = new CommentRequest("Test comment");

        mockPageable = PageRequest.of(0, 10);
    }

    @Test
    public void testGetRootComments() {

        User mockUser = new User();
        mockUser.setId(3L);
        Integer mockReplies = 2;

        Long commentId = 1L;
        String commentText = "Example comment text";
        LocalDateTime commentDate = LocalDateTime.now();
        boolean isAltered = false;
        int commentDepth = 0;

        CommentResponse commentResponse = new CommentResponse(
                commentId,
                commentText,
                commentDate,
                isAltered,
                userResponse,
                commentDepth,
                mockReplies
        );

        Page<CommentResponse> commentResponsePage = new PageImpl<>(Collections.singletonList(commentResponse));

        when(commentService.getRootComments(any(Long.class), any(Pageable.class))).thenReturn(ResponseEntity.ok(commentResponsePage));

        ResponseEntity<Page<CommentResponse>> response = commentController.getRootComments(1L, mockPageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(commentResponsePage);
    }


}
