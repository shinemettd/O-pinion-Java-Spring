package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.PasswordResetToken;
import kg.nurtelecom.opinion.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String passwordResetToken);
    Optional<PasswordResetToken> findByUser(User user);
}
