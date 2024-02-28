package kg.nurtelecom.opinion.controller.api;


import kg.nurtelecom.opinion.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagControllerTest {

    @Mock
    private TagService tagService;

    @InjectMocks
    private TagController tagController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    public void testGetAllTags() {
//
//        List<TagResponse> mockTags = Arrays.asList(
//                new TagResponse(1L, "tag1"),
//                new TagResponse(2L, "tag2"),
//                new TagResponse(3L, "tag3")
//        );
//
//
//        when(tagService.getAll()).thenReturn(ResponseEntity.ok(mockTags));
//
//        ResponseEntity<List<TagResponse>> responseEntity = tagController.getAllTags();
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//
//        assertEquals(mockTags, responseEntity.getBody());
//    }
}
