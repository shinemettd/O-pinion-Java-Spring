package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.announcement.AnnouncementGetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AnnouncementService {
    ResponseEntity<Page<AnnouncementGetDTO>> getAnnouncements(Pageable pageable, User user);

    ResponseEntity<AnnouncementGetDTO> getAnnouncement(Long id, User user);

}
