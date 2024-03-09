package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.announcement.AnnouncementResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AnnouncementService {
    ResponseEntity<Page<AnnouncementResponse>> getAnnouncements(Pageable pageable, User user);

    ResponseEntity<AnnouncementResponse> getAnnouncement(Long id, User user);

}
