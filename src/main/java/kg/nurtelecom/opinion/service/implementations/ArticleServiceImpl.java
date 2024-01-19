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
import kg.nurtelecom.opinion.service.ImageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    private final ImageService imageService;

    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository, ArticleReactionRepository articleReactionRepository, SavedArticlesRepository savedArticlesRepository, ArticleCommentRepository articleCommentRepository, ArticleMapper articleMapper, ImageService imageService) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.articleReactionRepository = articleReactionRepository;
        this.savedArticlesRepository = savedArticlesRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.articleMapper = articleMapper;
        this.imageService = imageService;
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
        Page<ArticlesGetDTO> response = articleMapper.toArticlesGetDTOPage(articles);
        response.forEach(article -> {
           addInformationToResponse(article, user);
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
    public ResponseEntity<ArticleGetDTO> getArticle(Long id, User user) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isEmpty() || article.get().getStatus() == ArticleStatus.DELETED) {
            throw new NotFoundException("Статьи с таким id не существует ");
        }
        // прибавляем один просмотр
        articleRepository.incrementViewsCount(id);
        ArticleGetDTO response = articleMapper.toArticleGetDTO(article.get());
        addInformationToResponse(response, user);
        return ResponseEntity.ok(response);
    }

    private Long calculateRating(Long articleId) {
        Long articleLikes = articleReactionRepository
                .countByArticleIdAndReactionType(articleId, ReactionType.LIKE);
        Long articleDislikes = articleReactionRepository
                .countByArticleIdAndReactionType(articleId, ReactionType.DISLIKE);
        return articleLikes - articleDislikes;
    }

    private void setInFavourites(ArticlesGetDTO articleResponse, User user) {
        Optional<SavedArticle> savedArticle = user != null ? savedArticlesRepository.findByArticleIdAndUserId(articleResponse.getId(), user.getId()) : Optional.empty();
        if(savedArticle.isEmpty()) {
            articleResponse.setInFavourites(false);
        } else {
            articleResponse.setInFavourites(true);
        }
    }

    private void addInformationToResponse(ArticlesGetDTO response, User currentUser) {
        Long articleId = response.getId();
        response.setRating(calculateRating(articleId));
        setInFavourites(response, currentUser);
        response.setTotalFavourites(savedArticlesRepository.countByArticleId(articleId));
        response.setTotalComments(articleCommentRepository.countByArticleId(articleId));
    }


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
    public ResponseEntity<Page<ArticlesGetDTO>> getMyArticles(User user, Pageable pageable) {
        Page<Article> articles = articleRepository.findByAuthor(user, pageable);
        Page<ArticlesGetDTO> response = articleMapper.toArticlesGetDTOPage(articles);
        response.forEach(article -> {
            addInformationToResponse(article, user);
        });
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> addCoverImage(Long articleId, MultipartFile image, User user) {
        Optional<Article> article =  articleRepository.findById(articleId);
        Article articleEntity = article.get();
        if(article.isEmpty() || articleEntity.getStatus() == ArticleStatus.BLOCKED || articleEntity.getStatus() ==  ArticleStatus.DELETED) {
            throw new NotFoundException("Статьи с таким id не существует");
        }
        // проверяем точно ли пользователь хочет добавить фото к своей статье
        if(articleEntity.getAuthor().getId() != user.getId()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // необходимо удалять прежнее фото если оно есть !!!!!
        String imagePath = imageService.loadImage(image);
        System.out.println(imagePath);
        articleEntity.setCoverImage(imagePath);

        return null;
    }
}
