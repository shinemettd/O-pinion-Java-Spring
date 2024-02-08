package kg.nurtelecom.opinion;

import kg.nurtelecom.opinion.controller.api.CommentController;
import kg.nurtelecom.opinion.controller.api.ImageController;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.comment.CommentRequest;
import kg.nurtelecom.opinion.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;


public class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private User mockUser;

    private CommentRequest mockCommentRequest;

    private Pageable mockPageable;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockUser = new User();
        mockCommentRequest = new CommentRequest("Test comment");

    }

}
