package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.UserNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {
    void deleteAllByUserId(Long id);

    Long countByUserIdAndIsReadIsFalse(Long id);

    Page<UserNotification> findAllByUserId(Long id, Pageable pageable);

    List<UserNotification> findAllByUserId(Long id);

    Page<UserNotification> findAllByUserIdAndIsReadFalse(Long id, Pageable pageable);

    Page<UserNotification> findAllByUserIdAndIsReadTrue(Long id, Pageable pageable);
}
