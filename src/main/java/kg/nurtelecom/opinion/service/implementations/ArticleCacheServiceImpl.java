package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.repository.ArticleRepository;
import kg.nurtelecom.opinion.service.ArticleCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;



@Service
@CacheConfig(cacheNames = "articles")
public class ArticleCacheServiceImpl implements ArticleCacheService {
    private final ArticleRepository articleRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final CacheManager cacheManager;
    private final Logger logger = LoggerFactory.getLogger(ArticleCacheServiceImpl.class);

    public ArticleCacheServiceImpl(ArticleRepository articleRepository, RedisTemplate<String, Object> redisTemplate, CacheManager cacheManager) {
        this.articleRepository = articleRepository;
        this.redisTemplate = redisTemplate;
        this.cacheManager = cacheManager;
    }

    @Override
    @Cacheable(key = "#id")
    public Article getArticle(Long id) {
        logger.info("No " + id + " article in cache");
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Статьи с таким id не существует"));
        return article;
    }


    @CachePut(key = "#article.id")
    public Article save(Article article) {
        logger.info("Caching article " + article.getId());
        return article;
    }



    @Transactional
    @Scheduled(fixedRateString = "${caching.spring.articlesTTL}")
    public void saveAllFromCacheToDB() {
        List<Article> articlesFromCache = getAllArticlesFromCache();
        for(Article article : articlesFromCache) {
            articleRepository.save(article);
        }
        if(articlesFromCache.isEmpty()) {
            logger.info("No articles in cache");
        }
        logger.info("Saved articles in DB");
        clearArticlesCache();
    }

    public List<Article> getAllArticlesFromCache() {
        Set<String> keys = redisTemplate.keys("galina:articles:*");
        for(String key : keys) {
            logger.info("KEY : " + key);
        }
        List<Article> articles = new ArrayList<>();
        for (String key : keys) {
            ValueOperations<String, Object> valueOps =  redisTemplate.opsForValue();
            Object value =  valueOps.get(key);
            Article article = (Article) value;
            articles.add(article);
        }
        return articles;
    }

    public void clearArticlesCache() {
        cacheManager.getCache("articles").clear();
        logger.info("Cache clean");
    }

    @Override
    public void clearArticleFromCache(String cacheKey) {
        cacheManager.getCache("articles").evict(cacheKey);
        System.out.println("Очистили статью из кэша");
    }


}
