package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.service.ArticleCacheService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;



@Service
@CacheConfig(cacheNames = "articles")
public class ArticleCacheServiceImpl implements ArticleCacheService {

    @Override
    @Cacheable(key = "#id")
    public Article getArticle(Long id) {
        System.out.println("ID " + id);
        return null;
    }

    @CachePut(key = "#article.id")
    public Article save(Article article) {
        return article;
    }
}
