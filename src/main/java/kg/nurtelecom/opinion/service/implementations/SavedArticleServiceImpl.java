package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.SavedArticle;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.mapper.SavedArticleMapper;
import kg.nurtelecom.opinion.payload.saved_article.SavedArticleResponse;
import kg.nurtelecom.opinion.repository.ArticleRepository;
import kg.nurtelecom.opinion.repository.SavedArticlesRepository;
import kg.nurtelecom.opinion.service.SavedArticlesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class SavedArticleServiceImpl implements SavedArticlesService {
    private final ArticleRepository articleRepository;
    private final SavedArticlesRepository savedArticlesRepository;
    private final SavedArticleMapper savedArticleMapper;

    public SavedArticleServiceImpl(ArticleRepository articleRepository, SavedArticlesRepository savedArticlesRepository, SavedArticleMapper savedArticleMapper) {
        this.articleRepository = articleRepository;
        this.savedArticlesRepository = savedArticlesRepository;
        this.savedArticleMapper = savedArticleMapper;
    }

    @Override
    public ResponseEntity<Page<SavedArticleResponse>> getSavedArticles(User user, Pageable pageable) {
        Page<SavedArticle> savedArticles = savedArticlesRepository.findAllByUser(user, pageable);
        Page<SavedArticleResponse> savedArticleResponses = savedArticleMapper.toSavedArticleResponsePage(savedArticles);

        return ResponseEntity.ok(savedArticleResponses);
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
