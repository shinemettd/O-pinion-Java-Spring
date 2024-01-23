package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.SavedArticle;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.ArticleStatus;
import kg.nurtelecom.opinion.enums.ReactionType;
import kg.nurtelecom.opinion.exception.FileReadingException;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.mapper.ArticleMapper;
import kg.nurtelecom.opinion.payload.article.*;
import kg.nurtelecom.opinion.repository.*;
import kg.nurtelecom.opinion.service.ArticleService;
import kg.nurtelecom.opinion.service.EmailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
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
    private final EmailService emailService;


    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository, ArticleReactionRepository articleReactionRepository, SavedArticlesRepository savedArticlesRepository, ArticleCommentRepository articleCommentRepository, ArticleMapper articleMapper, EmailService emailService) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.articleReactionRepository = articleReactionRepository;
        this.savedArticlesRepository = savedArticlesRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.articleMapper = articleMapper;
        this.emailService = emailService;
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
    public ResponseEntity<ArticleResponse> setContent(Long articleId, MultipartFile content) {
        Article articleEntity = isArticleExist(articleId);
        articleEntity.setContent(readHtml(content));
        return new ResponseEntity<>(articleMapper.toModel(articleEntity), HttpStatus.CREATED);
    }

    private String readHtml(MultipartFile htmlContent) {
        try {
            byte[] bytes = htmlContent.getBytes();
            return new String(bytes, "UTF-8");
        } catch (IOException e) {
            throw new FileReadingException("Ошибка во время чтения контента статьи");
        }

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
        Article articleEntity = isArticleExist(id);
        articleEntity.setTitle(editedArticle.title());
        articleEntity.setShortDescription(editedArticle.shortDescription());

        articleEntity = articleRepository.save(articleEntity);

        return ResponseEntity.ok(articleMapper.toModel(articleEntity));
    }


    @Override
    public ResponseEntity<ArticleGetDTO> getArticle(Long id, User user) {
        Article article = isArticleExist(id);
        // прибавляем один просмотр
        articleRepository.incrementViewsCount(id);
        ArticleGetDTO response = articleMapper.toArticleGetDTO(article);
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
        Article article = isArticleExist(id);
        article.setStatus(ArticleStatus.DELETED);
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

        emailService.sendEmail(recipient, articleUrl, from);

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
