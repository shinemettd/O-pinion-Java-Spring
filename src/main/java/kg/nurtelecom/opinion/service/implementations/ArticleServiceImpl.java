package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.SavedArticle;
import kg.nurtelecom.opinion.entity.Tag;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.ArticleStatus;
import kg.nurtelecom.opinion.enums.ReactionType;
import kg.nurtelecom.opinion.enums.Status;
import kg.nurtelecom.opinion.exception.NoAccessException;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.exception.NotValidException;
import kg.nurtelecom.opinion.mapper.ArticleMapper;
import kg.nurtelecom.opinion.mapper.TagMapper;
import kg.nurtelecom.opinion.mapper.UserMapper;
import kg.nurtelecom.opinion.payload.article.*;
import kg.nurtelecom.opinion.payload.tag.TagDTO;
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
import org.springframework.web.bind.MethodArgumentNotValidException;

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
    private final TagRepository tagRepository;
    private final ArticleMapper articleMapper;
    private final UserMapper userMapper;
    private final TagMapper tagMapper;
    private final MailSenderService mailSenderService;


    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository, ArticleReactionRepository articleReactionRepository, SavedArticlesRepository savedArticlesRepository, ArticleCommentRepository articleCommentRepository, TagRepository tagRepository, ArticleMapper articleMapper, UserMapper userMapper, TagMapper tagMapper, MailSenderService mailSenderService) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.articleReactionRepository = articleReactionRepository;
        this.savedArticlesRepository = savedArticlesRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.tagRepository = tagRepository;
        this.articleMapper = articleMapper;
        this.userMapper = userMapper;
        this.tagMapper = tagMapper;
        this.mailSenderService = mailSenderService;
    }

    @Override
    public ResponseEntity<ArticleResponse> createArticle(ArticleRequest article, User user) {
        Article articleEntity = articleMapper.toEntity(article);
        List<Tag> requestTags = articleEntity.getTags();
        List<Tag> entityTags = new ArrayList<>();
        for(Tag tag : requestTags) {
            Optional<Tag> tagEntity = tagRepository.findById(tag.getId());
            if(tagEntity.isPresent() && !entityTags.stream().anyMatch(entityTag -> entityTag.getId().equals(tag.getId()))) {
                entityTags.add(tagEntity.get());
            }
        }
        articleEntity.setTags(entityTags);
        articleEntity.setAuthor(user);
        articleEntity.setViewsCount(0l);
        articleEntity.setStatus(ArticleStatus.ON_MODERATION);
        articleEntity = articleRepository.save(articleEntity);

        return new ResponseEntity<>(articleMapper.toModel(articleEntity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ArticleResponse> createArticleDraft(ArticleDraftRequest article, User user) {
        Article articleEntity = articleMapper.toEntityFromDraftRequest(article);
        List<Tag> requestTags = articleEntity.getTags();
        List<Tag> entityTags = new ArrayList<>();
        for(Tag tag : requestTags) {
            Optional<Tag> tagEntity = tagRepository.findById(tag.getId());
            if(tagEntity.isPresent() && !entityTags.stream().anyMatch(entityTag -> entityTag.getId().equals(tag.getId()))) {
                entityTags.add(tagEntity.get());
            }
        }
        articleEntity.setTags(entityTags);
        articleEntity.setAuthor(user);
        articleEntity.setStatus(ArticleStatus.DRAFT);
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

        Page<ArticlesGetDTO> response = new PageImpl<>(articlesList, pageable, articles.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Page<ArticlesGetDTO>> searchArticle(Pageable pageable, String searchQuery, User user) {
        // поиск по тегам
        Page<Article> foundArticles = articleRepository.findByStatusAndTitleContaining(ArticleStatus.APPROVED, searchQuery,  pageable);
        List<ArticlesGetDTO> articlesList = new ArrayList<>();
        foundArticles.forEach(article -> {
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

        Page<ArticlesGetDTO> response = new PageImpl<>(articlesList, pageable, foundArticles.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<ArticleResponse> editArticle(ArticleDraftRequest editedArticle, Long id, User user) {

        Article articleEntity = isArticleExist(id);
        if(articleEntity.getAuthor().getId().equals(user.getId()) || articleEntity.getStatus().equals(ArticleStatus.DELETED)) {
            List<TagDTO> requestTags = editedArticle.tags();
            List<Tag> entityTags = new ArrayList<>();
            for(TagDTO tag : requestTags) {
                Optional<Tag> tagEntity = tagRepository.findById(tag.id());
                if(tagEntity.isPresent() && !entityTags.stream().anyMatch(entityTag -> entityTag.getId().equals(tag.id()))) {
                    entityTags.add(tagEntity.get());
                }
            }
            articleEntity.setTags(entityTags);
            articleEntity.setTitle(editedArticle.title());
            articleEntity.setShortDescription(editedArticle.shortDescription());
            articleEntity.setContent(editedArticle.content());
            articleEntity.setStatus(ArticleStatus.DRAFT);
            articleEntity = articleRepository.save(articleEntity);
            return ResponseEntity.ok(articleMapper.toModel(articleEntity));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Void> undraftArticle(Long articleId, User user) {
        Article articleEntity = articleRepository.findById(articleId)
                .orElseThrow(() -> new NotFoundException("Статья с таким id не найдена"));
        if(!articleEntity.getAuthor().getId().equals(user.getId()) || !articleEntity.getStatus().equals(ArticleStatus.DRAFT)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        final int contentLength = articleEntity.getContent().length();
        final int shortDescriptionLength = articleEntity.getShortDescription().length();
        final int titleLength = articleEntity.getTitle().length();
        if(contentLength < Article.CONTENT_MIN_LENGTH || contentLength > Article.CONTENT_MAX_LENGTH) {
            throw new NotValidException("Контент статьи должен быть от " + Article.CONTENT_MIN_LENGTH + " до " + Article.CONTENT_MAX_LENGTH + " символов");
        }
        if(shortDescriptionLength < Article.SHORT_DESCRIPTION_MIN_LENGTH || shortDescriptionLength > Article.SHORT_DESCRIPTION_MAX_LENGTH) {
            throw new NotValidException("Краткое описание должно быть от " + Article.SHORT_DESCRIPTION_MIN_LENGTH + " до " + Article.SHORT_DESCRIPTION_MAX_LENGTH + " символов");
        }
        if(titleLength < Article.TITLE_MIN_LENGTH || titleLength > Article.TITLE_MAX_LENGTH) {
            throw new NotValidException("Название статьи должно быть от " + Article.TITLE_MIN_LENGTH + " до " + Article.TITLE_MAX_LENGTH + " символов");
        }
        articleEntity.setStatus(ArticleStatus.ON_MODERATION);
        articleEntity.setViewsCount(0l);
        return new ResponseEntity<>(HttpStatus.OK);

    }


    @Override
    public ResponseEntity<ArticleGetDTO> getArticle(Long id, User user) {
        Article article = isArticleExist(id);
        if (article.getStatus().equals(ArticleStatus.APPROVED) || (user != null && article.getAuthor().getId().equals(user.getId()))) {
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
                    setInFavourites(id, user), article.getContent(), tagMapper.toTagResponseList(article.getTags()));

            return ResponseEntity.ok(response);
        } else {
            throw new NoAccessException("Статья недоступна = (");
        }
    }

    @Override
    public ResponseEntity<Long> getArticleRating(Long id, User user) {
        Article article = isArticleExist(id);
        ArticleStatus articleStatus = article.getStatus();
        Long articleAuthorsId = article.getAuthor().getId();
        if (articleStatus.equals(ArticleStatus.APPROVED) ||
                (user != null && articleAuthorsId.equals(user.getId()))) {
            Long articleRating = calculateRating(id);

            return ResponseEntity.ok(articleRating);
        } else {
            throw new NoAccessException("У вас нет доступа к данной статье");
        }
    }

    @Override
    public ResponseEntity<Long> getArticleTotalFavourites(Long id) {
        Long totalFavourites = savedArticlesRepository.countByArticleId(id);
        return ResponseEntity.ok(totalFavourites);
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
    public ResponseEntity<Page<MyArticlesGetDTO>> getMyArticles(User user, Pageable pageable) {
        Page<Article> articles = articleRepository.findByAuthor(user, pageable);
        List<MyArticlesGetDTO> articlesList = new ArrayList<>();
        articles.forEach(article -> {
            Long id = article.getId();
            MyArticlesGetDTO articlesResponse = new MyArticlesGetDTO(
                    article.getStatus(),
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

        Page<MyArticlesGetDTO> response = new PageImpl<>(articlesList, pageable, articles.getTotalElements());
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
        String articleUrl = "http://143.110.182.202:9999/article/" + articleId;
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
        String articleUrl = "http://143.110.182.202:9999/article/" + articleId;

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
