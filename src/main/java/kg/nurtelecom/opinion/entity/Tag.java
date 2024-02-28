package kg.nurtelecom.opinion.entity;

import jakarta.persistence.*;
import kg.nurtelecom.opinion.enums.TagStatus;

import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag extends BaseEntity{
    @Column(name = "tag_name")
    private String name;
    @Enumerated(EnumType.STRING)
    private TagStatus status;

    @ManyToMany(mappedBy = "tags")
    Set<Article> articleSet;

    public Tag() {
    }

    public Tag(String name, TagStatus status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String tagName) {
        this.name = tagName;
    }

    public TagStatus getStatus() {
        return status;
    }

    public void setStatus(TagStatus status) {
        this.status = status;
    }

    public Set<Article> getArticleSet() {
        return articleSet;
    }

    public void setArticleSet(Set<Article> articleSet) {
        this.articleSet = articleSet;
    }
}
