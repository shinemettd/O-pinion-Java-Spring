package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.SavedArticle;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.ArticleStatus;
import kg.nurtelecom.opinion.enums.ReactionType;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.mapper.SavedArticleMapper;
import kg.nurtelecom.opinion.mapper.UserMapper;
import kg.nurtelecom.opinion.payload.article.ArticlesGetDTO;
import kg.nurtelecom.opinion.repository.ArticleCommentRepository;
import kg.nurtelecom.opinion.repository.ArticleReactionRepository;
import kg.nurtelecom.opinion.repository.ArticleRepository;
import kg.nurtelecom.opinion.repository.SavedArticlesRepository;
import kg.nurtelecom.opinion.service.SavedArticlesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SavedArticleServiceImpl implements SavedArticlesService {
    private final ArticleRepository articleRepository;
    private final SavedArticlesRepository savedArticlesRepository;
    private final SavedArticleMapper savedArticleMapper;
    private final UserMapper userMapper;
    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleReactionRepository articleReactionRepository;


    public SavedArticleServiceImpl(ArticleRepository articleRepository, SavedArticlesRepository savedArticlesRepository, SavedArticleMapper savedArticleMapper, UserMapper userMapper, ArticleCommentRepository articleCommentRepository, ArticleReactionRepository articleReactionRepository) {
        this.articleRepository = articleRepository;
        this.savedArticlesRepository = savedArticlesRepository;
        this.savedArticleMapper = savedArticleMapper;
        this.userMapper = userMapper;
        this.articleCommentRepository = articleCommentRepository;
        this.articleReactionRepository = articleReactionRepository;
    }

    @Override
    public ResponseEntity<Page<ArticlesGetDTO>> getSavedArticles(User user, Pageable pageable) {
        Page<SavedArticle> savedArticles = savedArticlesRepository.findAllByUserAndArticleStatus(user, ArticleStatus.APPROVED, pageable);
        List<ArticlesGetDTO> articlesList = new ArrayList<>();
        savedArticles.forEach(savedArticle -> {
            Article article = savedArticle.getArticle();
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
                    true);
            articlesList.add(articlesResponse);
        });

        Page<ArticlesGetDTO> response = new PageImpl<>(articlesList, pageable, savedArticles.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Long calculateRating(Long articleId) {
        Long articleLikes = articleReactionRepository
                .countByArticleIdAndReactionType(articleId, ReactionType.LIKE);
        Long articleDislikes = articleReactionRepository
                .countByArticleIdAndReactionType(articleId, ReactionType.DISLIKE);
        return articleLikes - articleDislikes;
    }
    @Override
    public ResponseEntity<Void> addArticleToSaved(Long articleId, User user) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new NotFoundException("Статья с айди " + articleId + " не найдена"));

        if (savedArticlesRepository.existsByArticleIdAndUserId(articleId, user.getId())) {
            savedArticlesRepository.deleteByArticleIdAndUserId(articleId, user.getId());
        } else {
            SavedArticle savedArticle = new SavedArticle(article, user);
            savedArticlesRepository.save(savedArticle);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> deleteArticleFromSaved(Long articleId, User user) {
        SavedArticle savedArticle = savedArticlesRepository.findByArticleIdAndUserId(articleId, user.getId())
                .orElseThrow(() -> new NotFoundException("Статья не добавлена в избранное"));

        savedArticlesRepository.delete(savedArticle);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
