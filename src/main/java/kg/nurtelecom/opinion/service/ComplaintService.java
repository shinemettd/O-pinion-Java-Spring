package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.complaint.ComplaintDTO;
import org.springframework.http.ResponseEntity;

public interface ComplaintService {

    ResponseEntity<ComplaintDTO> createComplaint(Long articleId, ComplaintDTO complaintDTO, User user);
}
