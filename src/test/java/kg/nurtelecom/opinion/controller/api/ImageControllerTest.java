package kg.nurtelecom.opinion.controller.api_tests;

import kg.nurtelecom.opinion.controller.api.ImageController;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ImageControllerTest {

    @Mock
    private ImageService imageService;

    @InjectMocks
    private ImageController imageController;

    private User mockUser;
    private MultipartFile mockMultipartFile;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockUser = new User();
        mockMultipartFile = new MockMultipartFile("photo", "test.jpg", "image/jpeg", "test".getBytes());
    }

//    @Test
//    public void testLoadImage() {
//
//        when(imageService.loadArticleImage(any(MultipartFile.class))).thenReturn("imagePath");
//
//        ResponseEntity<String> response = imageController.loadImage(mockMultipartFile);
//
//        verify(imageService).loadArticleImage(any(MultipartFile.class));
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals("imagePath", response.getBody());
//        assertNotNull(response.getBody());
//    }


    @Test
    public void testDeleteImage() {
        when(imageService.deleteImage(anyString())).thenReturn(ResponseEntity.ok().build());

        ResponseEntity<Void> response = imageController.deleteImage("imagePath");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateCoverImage() {
        when(imageService.updateCoverImage(anyLong(), any(MultipartFile.class), any(User.class)))
                .thenReturn(ResponseEntity.ok("imagePath"));

        ResponseEntity<String> response = imageController.updateCoverImage(1L, mockMultipartFile, mockUser);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("imagePath", response.getBody());
    }

    @Test
    public void testDeleteCoverImage() {
        when(imageService.deleteCoverImage(anyLong(), any(User.class))).thenReturn(ResponseEntity.ok().build());

        ResponseEntity<Void> response = imageController.deleteCoverImage(1L, mockUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testChangeUserAvatar() {

        when(imageService.changeUserAvatar(any(User.class), any(MultipartFile.class)))
                .thenReturn(ResponseEntity.ok().build());

        ResponseEntity<Void> response = imageController.changeUserAvatar(mockUser, mockMultipartFile);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}

