package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.SavedArticle;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.ArticleStatus;
import kg.nurtelecom.opinion.enums.ReactionType;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.mapper.ArticleMapper;
import kg.nurtelecom.opinion.payload.article.*;
import kg.nurtelecom.opinion.repository.*;
import kg.nurtelecom.opinion.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ArticleReactionRepository articleReactionRepository;
    private final SavedArticlesRepository savedArticlesRepository;

    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleMapper articleMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository, ArticleReactionRepository articleReactionRepository, SavedArticlesRepository savedArticlesRepository, ArticleCommentRepository articleCommentRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.articleReactionRepository = articleReactionRepository;
        this.savedArticlesRepository = savedArticlesRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.articleMapper = articleMapper;
    }

    @Override
    public ResponseEntity<ArticleResponse> createArticle(ArticleRequest article, User user) {
        Article articleEntity = articleMapper.toEntity(article);
        articleEntity.setAuthor(user);
        articleEntity.setViewsCount(0l);
        articleEntity.setStatus(ArticleStatus.ON_MODERATION);

        articleEntity = articleRepository.save(articleEntity);

        return new ResponseEntity<>(articleMapper.toModel(articleEntity), HttpStatus.CREATED);
    }

//    @Override
//    public ResponseEntity<Page<ArticlesGetResponse>> getArticles(Pageable pageable) {
//        Page<Article> articles = articleRepository.findByStatus(ArticleStatus.APPROVED, pageable);
//        Page<ArticlesGetResponse> articlesResponse = articleMapper.toArticlesGetResponsePage(articles);
//
//        return ResponseEntity.ok(articlesResponse);
//    }
    @Override
    public ResponseEntity<Page<ArticlesGetDTO>> getArticles(Pageable pageable, User user) {
        Page<Article> articles = articleRepository.findByStatus(ArticleStatus.APPROVED, pageable);
        Page<ArticlesGetDTO> response = articleMapper.toArticlesGetDTOPage(articles);
        // у каждой DTO проставить те поля которых нет в сущности
        response.forEach(article -> {
           Long articleId = article.getId();
           Long articleLikes = articleReactionRepository
                   .countByArticleIdAndReactionType(articleId, ReactionType.LIKE);
           Long articleDislikes = articleReactionRepository
                   .countByArticleIdAndReactionType(articleId, ReactionType.DISLIKE);
           article.setRating(articleLikes - articleDislikes);

           Optional<SavedArticle> savedArticle = user != null ? savedArticlesRepository.findByArticleIdAndUserId(articleId, user.getId()) : Optional.empty();
           if(savedArticle.isEmpty()) {
               article.setInFavourites(false);
           } else {
               article.setInFavourites(true);
           }

           article.setTotalFavourites(savedArticlesRepository.countByArticleId(articleId));

           article.setTotalComments(articleCommentRepository.countByArticleId(articleId));
        });

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ArticleResponse> editArticle(ArticleRequest editedArticle, Long id) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isEmpty()) {
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
    public ResponseEntity<ArticleGetResponse> getArticle(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isEmpty() || article.get().getStatus() == ArticleStatus.DELETED) {
            throw new NotFoundException("Статьи с таким id не существует ");
        }
        return ResponseEntity.ok(articleMapper.toArticleGetResponse(article.get()));
    }

    @Transactional
    @Override
    public ResponseEntity<Void> deleteArticle(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isEmpty()) {
            throw new NotFoundException("Статьи с таким id не существует ");
        }
        article.get().setStatus(ArticleStatus.DELETED);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Page<ArticlesGetResponse>> getMyArticles(User user, Pageable pageable) {
        Page<Article> articles = articleRepository.findByAuthor(user, pageable);
        Page<ArticlesGetResponse> articlesResponse = articleMapper.toArticlesGetResponsePage(articles);

        return new ResponseEntity<>(articlesResponse, HttpStatus.FOUND);
    }
}
