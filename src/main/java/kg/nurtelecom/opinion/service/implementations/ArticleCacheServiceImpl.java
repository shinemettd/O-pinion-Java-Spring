package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.repository.ArticleRepository;
import kg.nurtelecom.opinion.service.ArticleCacheService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;



@Service
@CacheConfig(cacheNames = "articles")
public class ArticleCacheServiceImpl implements ArticleCacheService {
    private final ArticleRepository articleRepository;

    public ArticleCacheServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    @Cacheable(key = "#id")
    public Article getArticle(Long id) {
        System.out.println("статьи в кеше нет ");
        return updateArticleInCache(id);
    }

    @CachePut(key = "#article.id")
    public Article save(Article article) {
        System.out.println("Не сохранили статью в кэш ");
        return article;
    }

    private  Article updateArticleInCache(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Статьи с таким id не существует"));
        System.out.println("Достаем из бд");
        return save(article);
    }
}
