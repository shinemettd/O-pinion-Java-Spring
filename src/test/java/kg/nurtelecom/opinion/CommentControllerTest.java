package kg.nurtelecom.opinion;

import kg.nurtelecom.opinion.controller.api.CommentController;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.comment.CommentRequest;
import kg.nurtelecom.opinion.payload.comment.CommentResponse;
import kg.nurtelecom.opinion.payload.comment.ReplyCommentResponse;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


public class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private User mockUser;

    private Pageable mockPageable;

    private UserResponse userResponse;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockUser = new User();
        CommentRequest commentRequest = new CommentRequest("Test comment");

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

    @Test
    public void testGetCommentReplies() {
        List<ReplyCommentResponse> replies = Arrays.asList(
                new ReplyCommentResponse(1L, "Reply 1", LocalDateTime.now(), false, userResponse, 0),
                new ReplyCommentResponse(2L, "Reply 2", LocalDateTime.now(), false, userResponse, 0)
        );
        Page<ReplyCommentResponse> replyPage = new PageImpl<>(replies);

        when(commentService.getCommentReplies(any(Long.class), any(Pageable.class)))
                .thenReturn(ResponseEntity.ok(replyPage));


        ResponseEntity<Page<ReplyCommentResponse>> response =
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

        CommentResponse commentResponse = new CommentResponse(
                commentId,
                commentText,
                commentDate,
                isAltered,
                userResponse,
                commentDepth,
                mockReplies
        );

        when(commentService.updateCommentById(any(Long.class),
                any(CommentRequest.class),
                any(User.class)))
                .thenReturn(ResponseEntity.ok(commentResponse));


        ResponseEntity<CommentResponse> response =
                commentController.
                        updateCommentById(2L, new CommentRequest("Updated text"), mockUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }


    @Test
    public void testDeleteCommentById() {
        Long commentId = 2L;

        doNothing().when(commentService).deleteCommentById(any(Long.class), any(User.class));

        commentController.deleteCommentById(commentId, mockUser);

    }


}
