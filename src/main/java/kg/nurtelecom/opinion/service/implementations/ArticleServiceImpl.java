package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.ArticleStatus;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.mapper.ArticleMapper;
import kg.nurtelecom.opinion.mapper.ArticleGetMapper;
import kg.nurtelecom.opinion.payload.article.ArticleRequest;
import kg.nurtelecom.opinion.payload.article.ArticleResponse;
import kg.nurtelecom.opinion.payload.article.ArticleGetResponse;
import kg.nurtelecom.opinion.repository.ArticleRepository;
import kg.nurtelecom.opinion.repository.UserRepository;
import kg.nurtelecom.opinion.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ArticleMapper articleMapper;
    private final ArticleGetMapper articleGetMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository, ArticleMapper articleMapper, ArticleGetMapper articleGetMapper) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.articleMapper = articleMapper;
        this.articleGetMapper = articleGetMapper;
    }

    @Override
    public ResponseEntity<ArticleResponse> createArticle(ArticleRequest article, User user) {
        Article articleEntity = articleMapper.toEntity(article);
//        articleEntity.setAuthor(userRepository.findByEmail(user.getEmail()).get());
        articleEntity.setAuthor(userRepository.findById(1l).get()); // временно
        articleEntity.setViewsCount(0);
        articleEntity.setStatus(ArticleStatus.ON_MODERATION);

        articleEntity = articleRepository.save(articleEntity);

        return ResponseEntity.ok(articleMapper.toModel(articleEntity));
    }

    @Override
    public ResponseEntity<List<ArticleGetResponse>> getArticles() {
        // мы должны возвращать статьи только со статусом 'APPROVED'
        List<Article> articles = articleRepository.findByStatus(ArticleStatus.APPROVED);


        List<ArticleGetResponse> articlesResponse = new ArrayList<>();
        for(Article article : articles) {
            articlesResponse.add(articleGetMapper.toModel(article));
        }
        return ResponseEntity.ok(articlesResponse);
    }

    @Override
    public ResponseEntity<ArticleResponse> editArticle(ArticleRequest editedArticle, long id) {
        Optional<Article> article = articleRepository.findById(id);
        if(article.isEmpty()) {
            throw new NotFoundException("Статьи с таким id не существует ");
        }
        Article articleEntity = article.get();
        articleEntity.setTitle(editedArticle.title());
        articleEntity.setShortDescription(editedArticle.shortDescription());
        articleEntity.setContent(editedArticle.content());

        articleEntity = articleRepository.save(articleEntity);

        return ResponseEntity.ok(articleMapper.toModel(articleEntity));
    }

    @Override
    public ResponseEntity<ArticleGetResponse> getArticle(long id) {
        Optional<Article> article = articleRepository.findById(id);
        if(article.isEmpty()) {
            throw new NotFoundException("Статьи с таким id не существует ");
        }
        return ResponseEntity.ok(articleGetMapper.toModel(article.get()));
    }
}
