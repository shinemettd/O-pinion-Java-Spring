package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmailOrNickname(String email, String nickname);

    Optional<User> findByIdAndStatus(Long id, Status status);

    Optional<User> findByNicknameAndStatus(String nickname, Status status);

    Optional<User> findByNickname(String nickname);
}
