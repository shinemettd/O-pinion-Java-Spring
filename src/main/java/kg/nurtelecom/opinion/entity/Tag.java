package kg.nurtelecom.opinion.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag extends BaseEntity{
    @Column(name = "tag_name")
    private String tagName;

    @ManyToMany(mappedBy = "tagSet")
    Set<Article> articleSet;

    public Tag() {
    }

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Set<Article> getArticleSet() {
        return articleSet;
    }

    public void setArticleSet(Set<Article> articleSet) {
        this.articleSet = articleSet;
    }
}
