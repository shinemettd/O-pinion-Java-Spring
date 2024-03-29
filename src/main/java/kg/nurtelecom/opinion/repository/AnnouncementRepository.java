package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.Announcement;
import kg.nurtelecom.opinion.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    @Modifying
    @Query("UPDATE Announcement a SET a.viewsCount = a.viewsCount + 1 WHERE a.id = :announcementId")
    void incrementViewsCount(@Param("announcementId") Long announcementId);
}
