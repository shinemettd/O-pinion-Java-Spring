package kg.nurtelecom.opinion.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import kg.nurtelecom.opinion.enums.ArticleStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "article")
public class Article extends BaseEntity {
    private String title;
    @Column(name = "short_description")
    private String shortDescription;
    private String content;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @Enumerated(EnumType.STRING)
    private ArticleStatus status;

    @Column(name = "views_count")
    private int viewsCount;
}
