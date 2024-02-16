package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.SavedArticle;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.ArticleStatus;
import kg.nurtelecom.opinion.enums.ReactionType;
import kg.nurtelecom.opinion.enums.Status;
import kg.nurtelecom.opinion.exception.FileException;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.mapper.ArticleMapper;
import kg.nurtelecom.opinion.mapper.UserMapper;
import kg.nurtelecom.opinion.payload.article.*;
import kg.nurtelecom.opinion.repository.*;
import kg.nurtelecom.opinion.service.ArticleService;
import kg.nurtelecom.opinion.service.MailSenderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ArticleReactionRepository articleReactionRepository;
    private final SavedArticlesRepository savedArticlesRepository;

    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleMapper articleMapper;
    private final UserMapper userMapper;
    private final MailSenderService mailSenderService;


    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository, ArticleReactionRepository articleReactionRepository, SavedArticlesRepository savedArticlesRepository, ArticleCommentRepository articleCommentRepository, ArticleMapper articleMapper, UserMapper userMapper, MailSenderService mailSenderService) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.articleReactionRepository = articleReactionRepository;
        this.savedArticlesRepository = savedArticlesRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.articleMapper = articleMapper;
        this.userMapper = userMapper;
        this.mailSenderService = mailSenderService;
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

    
    @Override
    public ResponseEntity<Page<ArticlesGetDTO>> getArticles(Pageable pageable, User user) {
        Page<Article> articles = articleRepository.findByStatus(ArticleStatus.APPROVED, pageable);
        List<ArticlesGetDTO> articlesList = new ArrayList<>();
        articles.forEach(article -> {
            Long id = article.getId();
            ArticlesGetDTO articlesResponse = new ArticlesGetDTO(
                    article.getId(),
                    article.getTitle(),
                    article.getShortDescription(),
                    article.getCoverImage(),
                    article.getDateTime(),
                    userMapper.toUserResponse(article.getAuthor()),
                    calculateRating(id),
                    savedArticlesRepository.countByArticleId(article.getId()),
                    articleCommentRepository.countByArticleId(id),
                    article.getViewsCount(),
                    setInFavourites(id, user));
            articlesList.add(articlesResponse);
        });

        // Создаем объект PageImpl, используя конструктор с параметрами
        Page<ArticlesGetDTO> response = new PageImpl<>(articlesList, pageable, articles.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ArticleResponse> editArticle(ArticleRequest editedArticle, Long id, User user) {
        Article articleEntity = isArticleExist(id);
        if(articleEntity.getAuthor().getId().equals(user.getId())) {
            articleEntity.setTitle(editedArticle.title());
            articleEntity.setShortDescription(editedArticle.shortDescription());
            articleEntity.setContent(editedArticle.content());
            articleEntity = articleRepository.save(articleEntity);
            return ResponseEntity.ok(articleMapper.toModel(articleEntity));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @Override
    public ResponseEntity<ArticleGetDTO> getArticle(Long id, User user) {
        Article article = isArticleExist(id);
        articleRepository.incrementViewsCount(id);
        ArticleGetDTO response = new ArticleGetDTO(
                article.getId(),
                article.getTitle(),
                article.getShortDescription(),
                article.getCoverImage(),
                article.getDateTime(),
                userMapper.toUserResponse(article.getAuthor()),
                calculateRating(id),
                savedArticlesRepository.countByArticleId(id),
                articleCommentRepository.countByArticleId(id),
                article.getViewsCount(),
                setInFavourites(id, user), article.getContent());

        return ResponseEntity.ok(response);
    }

    private Long calculateRating(Long articleId) {
        Long articleLikes = articleReactionRepository
                .countByArticleIdAndReactionType(articleId, ReactionType.LIKE);
        Long articleDislikes = articleReactionRepository
                .countByArticleIdAndReactionType(articleId, ReactionType.DISLIKE);
        return articleLikes - articleDislikes;
    }

    private boolean setInFavourites(Long articleId, User user) {
        Optional<SavedArticle> savedArticle = user != null ? savedArticlesRepository.findByArticleIdAndUserId(articleId, user.getId()) : Optional.empty();
        if(savedArticle.isEmpty()) {
           return false;
        } else {
            return true;
        }
    }



    @Override
    public ResponseEntity<Void> deleteArticle(Long id, User user) {
        Article article = isArticleExist(id);
        if(article.getAuthor().getId().equals(user.getId())) {
            article.setStatus(ArticleStatus.DELETED);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Page<ArticlesGetDTO>> getMyArticles(User user, Pageable pageable) {
        Page<Article> articles = articleRepository.findByAuthor(user, pageable);
        List<ArticlesGetDTO> articlesList = new ArrayList<>();
        articles.forEach(article -> {
            Long id = article.getId();
            ArticlesGetDTO articlesResponse = new ArticlesGetDTO(
                    article.getId(),
                    article.getTitle(),
                    article.getShortDescription(),
                    article.getCoverImage(),
                    article.getDateTime(),
                    userMapper.toUserResponse(article.getAuthor()),
                    calculateRating(id),
                    savedArticlesRepository.countByArticleId(article.getId()),
                    articleCommentRepository.countByArticleId(id),
                    article.getViewsCount(),
                    setInFavourites(id, user));
            articlesList.add(articlesResponse);
        });

        Page<ArticlesGetDTO> response = new PageImpl<>(articlesList, pageable, articles.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Page<ArticlesGetDTO>> getUserArticles(Long userId, Pageable pageable) {
        Optional<User> user = userRepository.findByIdAndStatus(userId, Status.VERIFIED);
        User userEntity = user.orElseThrow(() -> new NotFoundException("Пользователя с таким id не существует"));
        Page<Article> articles = articleRepository.findByAuthorAndStatus(userEntity, pageable, ArticleStatus.APPROVED);
        List<ArticlesGetDTO> articlesList = new ArrayList<>();
        articles.forEach(article -> {
            Long id = article.getId();
            ArticlesGetDTO articlesResponse = new ArticlesGetDTO(
                    article.getId(),
                    article.getTitle(),
                    article.getShortDescription(),
                    article.getCoverImage(),
                    article.getDateTime(),
                    userMapper.toUserResponse(article.getAuthor()),
                    calculateRating(id),
                    savedArticlesRepository.countByArticleId(article.getId()),
                    articleCommentRepository.countByArticleId(id),
                    article.getViewsCount(),
                    setInFavourites(id, userEntity));
            articlesList.add(articlesResponse);
        });

        // Создаем объект PageImpl, используя конструктор с параметрами
        Page<ArticlesGetDTO> response = new PageImpl<>(articlesList, pageable, articles.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> shareArticle(Long articleId, String shareType) {
        isArticleExist(articleId);
        String articleUrl = "http://194.152.37.7:8812/api/articles/" + articleId;
        switch (shareType){
            case("article"):
                return new ResponseEntity<>(articleUrl,  HttpStatus.OK);
            case("telegram"):
                return new ResponseEntity<>("https://t.me/share/url?url=" + articleUrl, HttpStatus.OK);
            case("whatsapp"):
                return new ResponseEntity<>("https://web.whatsapp.com/send?text=" + articleUrl, HttpStatus.OK);
            case("vk"):
                return new ResponseEntity<>("https://vk.com/share.php?url=" + articleUrl, HttpStatus.OK);
            default:
                return new ResponseEntity<>("Share type not found", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Void> shareArticleByEmail(Long articleId,String recipient, String from) {
        isArticleExist(articleId);
        String articleUrl = "http://194.152.37.7:8812/api/articles/" + articleId;

        mailSenderService.sendEmail(recipient, articleUrl, from);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Article isArticleExist(Long articleId) {
        Optional<Article> article = articleRepository.findById(articleId);
        if(article.isEmpty()) {
            throw new NotFoundException("Статьи с таким id не существует");
        }
        return article.get();
    }
}
