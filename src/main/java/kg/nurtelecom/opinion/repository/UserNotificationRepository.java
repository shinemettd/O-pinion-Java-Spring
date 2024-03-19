package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.entity.UserNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {
    void deleteAllByUserId(Long id);

    Page<UserNotification> findAllByUserId(Long id);
}
