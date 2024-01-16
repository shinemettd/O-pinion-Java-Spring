package kg.nurtelecom.opinion.entity;

import jakarta.persistence.*;
import kg.nurtelecom.opinion.enums.ImageType;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "article_images")
public class Image extends BaseEntity{
    private String path;
    @Enumerated(EnumType.STRING)
    private ImageType type;

    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private Article article;
}
