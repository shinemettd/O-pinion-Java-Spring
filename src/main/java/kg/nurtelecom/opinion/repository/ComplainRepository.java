package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.Complain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplainRepository extends JpaRepository<Complain, Long> {
}
