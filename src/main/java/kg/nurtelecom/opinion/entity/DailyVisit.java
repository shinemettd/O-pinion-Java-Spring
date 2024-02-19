package kg.nurtelecom.opinion.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "daily_visits")
public class DailyVisit extends BaseEntity {
    private LocalDate date;
    private Long visitCount;

    public DailyVisit() {
    }

    public DailyVisit(LocalDate date, Long visitCount) {
        this.date = date;
        this.visitCount = visitCount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Long visitCount) {
        this.visitCount = visitCount;
    }

    @Override
    public String toString() {
        return "DailyVisit{" +
                "date=" + date +
                ", visitCount=" + visitCount +
                '}';
    }
}
