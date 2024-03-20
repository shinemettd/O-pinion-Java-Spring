package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    boolean existsByUserIdAndArticleId(Long userId, Long articleId);
}
