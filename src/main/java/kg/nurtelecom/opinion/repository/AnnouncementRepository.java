package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.Announcement;
import kg.nurtelecom.opinion.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
