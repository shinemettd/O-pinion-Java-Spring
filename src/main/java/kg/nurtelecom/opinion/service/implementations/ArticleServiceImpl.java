package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.enums.ArticleStatus;
import kg.nurtelecom.opinion.mapper.ArticleMapper;
import kg.nurtelecom.opinion.payload.article.ArticleCreateRequest;
import kg.nurtelecom.opinion.payload.article.ArticleCreateResponse;
import kg.nurtelecom.opinion.repository.ArticleRepository;
import kg.nurtelecom.opinion.repository.UserRepository;
import kg.nurtelecom.opinion.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ArticleMapper articleMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.articleMapper = articleMapper;
    }

    @Override
    public ResponseEntity<ArticleCreateResponse> createArticle(ArticleCreateRequest article) {
        Article articleEntity = articleMapper.toEntity(article);
        // нужно вытащить человека который сейчас отправил запрос по id
        // пока положим первого пользователя как автора для всех статей
        articleEntity.setAuthor(userRepository.findById(1l).get());
//        articleEntity.setDateTime();
        articleEntity.setViewsCount(0);
        articleEntity.setStatus(ArticleStatus.ON_MODERATION);

        articleEntity = articleRepository.save(articleEntity);

        return ResponseEntity.ok(articleMapper.toModel(articleEntity));
    }
}
