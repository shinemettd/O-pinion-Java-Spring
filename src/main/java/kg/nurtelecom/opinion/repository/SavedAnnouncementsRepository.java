package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.Announcement;
import kg.nurtelecom.opinion.entity.SavedAnnouncement;
import kg.nurtelecom.opinion.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedAnnouncementsRepository extends JpaRepository<SavedAnnouncement, Long> {

    Page<SavedAnnouncement> findAllByUser(Pageable pageable, User user);
    boolean existsByAnnouncementIdAndUserId(Long announcementId, Long userId);
    void deleteByAnnouncementIdAndUserId(Long announcementId, Long id);
}
