package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmailOrNickname(String email, String nickname);

}
