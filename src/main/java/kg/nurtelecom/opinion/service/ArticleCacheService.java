package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.Article;

import java.util.List;

public interface ArticleCacheService {
    Article getArticle(Long id);

    Article save(Article article);

    void saveAllFromCacheToDB();

    List<Article> getAllArticlesFromCache();

    void clearArticlesCache();
    void clearArticleFromCache(String cacheKey);
}
