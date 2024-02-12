package kg.nurtelecom.opinion.controller.api;

import kg.nurtelecom.opinion.entity.Announcement;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.announcement.AnnouncementGetDTO;
import kg.nurtelecom.opinion.service.AnnouncementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnnouncementControllerTest {

    @InjectMocks
    AnnouncementController announcementController;

    @Mock
    AnnouncementService announcementService;

    @Mock
    User mockUser;

    @Test
    void getAnnouncementsShouldReturnValidEntity() {
        Page<AnnouncementGetDTO> mockPage = mock(Page.class);
        Pageable mockPageable = mock(Pageable.class);
        ResponseEntity<Page<AnnouncementGetDTO>> expectedResponse = ResponseEntity.ok(mockPage);

        when(announcementService.getAnnouncements(mockPageable, this.mockUser)).thenReturn(expectedResponse);

        ResponseEntity<Page<AnnouncementGetDTO>> response = announcementController.getAnnouncements(mockPageable, this.mockUser);

        verify(announcementService, times(1)).getAnnouncements(mockPageable, this.mockUser);
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAnnouncementShouldReturnValidEntity() {
        Long mockId = 1L;
        AnnouncementGetDTO mockAnnouncementGetDTO = mock(AnnouncementGetDTO.class);
        ResponseEntity<AnnouncementGetDTO> expectedResponse = ResponseEntity.ok(mockAnnouncementGetDTO);

        when(announcementService.getAnnouncement(mockId, this.mockUser)).thenReturn(expectedResponse);

        ResponseEntity<AnnouncementGetDTO> response = announcementController.getAnnouncement(mockId, this.mockUser);

        verify(announcementService, times(1)).getAnnouncement(mockId, this.mockUser);
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}