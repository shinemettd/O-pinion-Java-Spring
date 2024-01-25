package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.entity.UserPrivacySettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPrivacyRepository extends JpaRepository<UserPrivacySettings, Long> {
    Optional<UserPrivacySettings> getUserPrivacySettingsByUser(User user);
}
