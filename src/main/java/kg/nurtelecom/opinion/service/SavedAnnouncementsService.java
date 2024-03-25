package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.announcement.AnnouncementResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface SavedAnnouncementsService {
    ResponseEntity<Page<AnnouncementResponse>> getSavedAnnouncements(User user, Pageable pageable);
    ResponseEntity<Void> addAnnouncementToSaved(Long announcement_id, User user);

    ResponseEntity<Void> deleteAnnouncementFromSaved(Long announcement_id, User user);

}
