package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.UserNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {
    void deleteAllByUserId(Long id);

    Page<UserNotification> findAllByUserId(Long id, Pageable pageable);

    Page<UserNotification> findAllByUserIdAndIsReadFalse(Long id, Pageable pageable);

    Page<UserNotification> findAllByUserIdAndIsReadTrue(Long id, Pageable pageable);
}
