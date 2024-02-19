package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.DailyVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DailyVisitRepository extends JpaRepository<DailyVisit, Long> {
    Optional<DailyVisit> findByDate(LocalDate date);
}

