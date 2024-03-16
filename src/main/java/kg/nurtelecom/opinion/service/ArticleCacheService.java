package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.Article;

public interface ArticleCacheService {
    Article getArticle(Long id);

    Article save(Article article);
}
