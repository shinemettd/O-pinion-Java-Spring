package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.UserPrivacySettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPrivacyRepository extends JpaRepository<UserPrivacySettings, Long> {

}
