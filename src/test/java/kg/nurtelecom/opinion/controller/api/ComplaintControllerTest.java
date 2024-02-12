package kg.nurtelecom.opinion.controller.api;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.Reason;
import kg.nurtelecom.opinion.payload.complaint.ComplaintDTO;
import kg.nurtelecom.opinion.service.ComplaintService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComplaintControllerTest {
    @Mock
    ComplaintService complaintService;
    @InjectMocks
    ComplaintController complaintController;

    @Mock
    User mockUser;

    @Mock
    Article mockArticle;

    @Test
    void createComplainShouldReturnValidResponseEntityWithReasonSpam() {
        ComplaintDTO complaintDTO = new ComplaintDTO(Reason.SPAM, "Some test text about spam");
        ResponseEntity<ComplaintDTO> expectedResponse = ResponseEntity.ok(complaintDTO);

        when(complaintService.createComplaint(this.mockArticle.getId(), complaintDTO, this.mockUser)).thenReturn(expectedResponse);

        ResponseEntity<ComplaintDTO> response = complaintController.createComplain(this.mockArticle.getId(), complaintDTO, this.mockUser);

        verify(complaintService, times(1)).createComplaint(this.mockArticle.getId(), complaintDTO, this.mockUser);
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void createComplainShouldReturnValidResponseEntityWithReasonOther() {
        ComplaintDTO complaintDTO = new ComplaintDTO(Reason.OTHER, "Some test text about other");
        ResponseEntity<ComplaintDTO> expectedResponse = ResponseEntity.ok(complaintDTO);

        when(complaintService.createComplaint(this.mockArticle.getId(), complaintDTO, this.mockUser)).thenReturn(expectedResponse);

        ResponseEntity<ComplaintDTO> response = complaintController.createComplain(this.mockArticle.getId(), complaintDTO, this.mockUser);

        verify(complaintService, times(1)).createComplaint(this.mockArticle.getId(), complaintDTO, this.mockUser);
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}