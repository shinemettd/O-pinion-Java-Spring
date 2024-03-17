package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.repository.ArticleRepository;
import kg.nurtelecom.opinion.service.ArticleCacheService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    public ArticleCacheServiceImpl(ArticleRepository articleRepository, RedisTemplate<String, Object> redisTemplate) {
        this.articleRepository = articleRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    @Cacheable(key = "#id")
    public Article getArticle(Long id) {
        System.out.println("статьи в кеше нет ");
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Статьи с таким id не существует"));
        return article;
    }


    @CachePut(key = "#article.id")
    public Article save(Article article) {
        System.out.println("Не сохранили статью в кэш ");
        return article;
    }


    @Transactional
    @Scheduled(fixedRateString = "${caching.spring.articlesTTL}")
    public void saveFromCacheToDB() {
        List<Article> articlesFromCache = getAllArticlesFromCache();
        for(Article article : articlesFromCache) {
            System.out.println("Article id : " + article.getId());
            System.out.println("Article title : " + article.getTitle());
            articleRepository.save(article);
        }
        if(articlesFromCache.isEmpty()) {
            System.out.println("Нет статей в кэше");
        }
        System.out.println("Сохранили все статьи в БД");
        clearArticlesCache();
        System.out.println("Очистили кэш");
    }

    private List<Article> getAllArticlesFromCache() {
        Set<String> keys = redisTemplate.keys("galina:articles:*");
        if(keys.isEmpty()) {
            System.out.println("Нет ключей таких в кэше");
        }
        for(String key : keys) {
            System.out.println("Key : " + key);
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



    @CacheEvict(allEntries = true)
    public void clearArticlesCache() {

    }
}
