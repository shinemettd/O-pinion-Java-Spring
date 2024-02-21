package kg.nurtelecom.opinion.entity;

import jakarta.persistence.*;
import kg.nurtelecom.opinion.enums.TagStatus;

import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag extends BaseEntity{
    @Column(name = "tag_name")
    private String tagName;
    @Enumerated(EnumType.STRING)
    private TagStatus status;

    @ManyToMany(mappedBy = "tagSet")
    Set<Article> articleSet;

    public Tag() {
    }

    public Tag(String tagName, TagStatus status) {
        this.tagName = tagName;
        this.status = status;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
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
