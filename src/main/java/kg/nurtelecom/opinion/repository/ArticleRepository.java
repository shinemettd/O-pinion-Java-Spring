package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findByAuthor(User user, Pageable pageable);
    Page<Article> findByStatus(@Param("status") ArticleStatus status, Pageable pageable);
}
