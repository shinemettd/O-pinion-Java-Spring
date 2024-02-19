package kg.nurtelecom.opinion.service.implementations;

import jakarta.transaction.Transactional;
import kg.nurtelecom.opinion.entity.DailyVisit;
import kg.nurtelecom.opinion.repository.DailyVisitRepository;
import kg.nurtelecom.opinion.service.DailyVisitService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class DailyVisitServiceImpl implements DailyVisitService {
    private final DailyVisitRepository dailyVisitRepository;
    private Long dailyVisitsCount;

    public DailyVisitServiceImpl(DailyVisitRepository dailyVisitRepository) {
        this.dailyVisitRepository = dailyVisitRepository;
    }

    public void incrementDailyVisitCount() {
        if (dailyVisitsCount == null) {
            dailyVisitsCount = 0L;
        }
        dailyVisitsCount++;
    }


    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void saveDailyVisitCountToDatabase() {
        LocalDate currentDate = LocalDate.now().minusDays(1);

        Optional<DailyVisit> optionalDailyVisit = dailyVisitRepository.findByDate(currentDate);
        DailyVisit dailyVisit = optionalDailyVisit.orElseGet(() -> {
            DailyVisit newDailyVisit = new DailyVisit();
            newDailyVisit.setDate(currentDate);
            newDailyVisit.setVisitCount(0L);
            return newDailyVisit;
        });
        dailyVisit.setVisitCount(dailyVisit.getVisitCount() + dailyVisitsCount);
        dailyVisitRepository.save(dailyVisit);
    }

}
