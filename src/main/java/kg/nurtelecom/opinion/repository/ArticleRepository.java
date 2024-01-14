package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
