package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.AnnouncementComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementCommentRepository extends JpaRepository<AnnouncementComment, Long> {
    @Query("SELECT c FROM AnnouncementComment c WHERE c.announcement.id = :announcementId ORDER BY c.date DESC")
    Page<AnnouncementComment> findComments(Long announcementId, Pageable pageable);
}
