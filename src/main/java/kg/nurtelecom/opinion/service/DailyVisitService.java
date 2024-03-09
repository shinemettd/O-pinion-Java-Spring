package kg.nurtelecom.opinion.service;

public interface DailyVisitService {
    void incrementDailyVisitCount();

    void saveDailyVisitCountToDatabase();
}

