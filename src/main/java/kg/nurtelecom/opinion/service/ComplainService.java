package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.payload.complain.ComplainDTO;
import org.springframework.http.ResponseEntity;

public interface ComplainService {

    ResponseEntity<ComplainDTO> createComplain(Long articleId, ComplainDTO complainDTO);
}
