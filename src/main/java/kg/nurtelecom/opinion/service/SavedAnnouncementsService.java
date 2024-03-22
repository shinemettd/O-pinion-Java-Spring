package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.saved_announcement.SavedAnnouncementResponse;
import kg.nurtelecom.opinion.payload.saved_article.SavedArticleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface SavedAnnouncementsService {
    ResponseEntity<Page<SavedAnnouncementResponse>> getSavedAnnouncements(User user, Pageable pageable);
    ResponseEntity<Void> addAnnouncementToSaved(Long announcement_id, User user);

    ResponseEntity<Void> deleteAnnouncementFromSaved(Long announcement_id, User user);

}
