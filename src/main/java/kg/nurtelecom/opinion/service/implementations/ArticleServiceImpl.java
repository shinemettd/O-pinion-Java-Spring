package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.ArticleStatus;
import kg.nurtelecom.opinion.mapper.ArticleCreateMapper;
import kg.nurtelecom.opinion.mapper.ArticleMapper;
import kg.nurtelecom.opinion.payload.article.ArticleCreateRequest;
import kg.nurtelecom.opinion.payload.article.ArticleCreateResponse;
import kg.nurtelecom.opinion.payload.article.ArticleGetResponse;
import kg.nurtelecom.opinion.repository.ArticleRepository;
import kg.nurtelecom.opinion.repository.UserRepository;
import kg.nurtelecom.opinion.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ArticleCreateMapper articleCreateMapper;
    private final ArticleMapper articleMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository, ArticleCreateMapper articleCreateMapper, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.articleCreateMapper = articleCreateMapper;
        this.articleMapper = articleMapper;
    }

    @Override
    public ResponseEntity<ArticleCreateResponse> createArticle(ArticleCreateRequest article, User user) {
        Article articleEntity = articleCreateMapper.toEntity(article);
//        articleEntity.setAuthor(userRepository.findByEmail(user.getEmail()).get());
        articleEntity.setAuthor(userRepository.findById(1l).get()); // временно
        articleEntity.setViewsCount(0);
        articleEntity.setStatus(ArticleStatus.ON_MODERATION);

        articleEntity = articleRepository.save(articleEntity);

        return ResponseEntity.ok(articleCreateMapper.toModel(articleEntity));
    }

    @Override
    public ResponseEntity<List<ArticleGetResponse>> getArticles() {
        // мы должны возвращать статьи только со статусом 'APPROVED'
        List<Article> articles = articleRepository.findByStatus(ArticleStatus.APPROVED);


        List<ArticleGetResponse> articlesResponse = new ArrayList<>();
        for(Article article : articles) {
            articlesResponse.add(articleMapper.toModel(article));
        }
        return ResponseEntity.ok(articlesResponse);
    }
}
