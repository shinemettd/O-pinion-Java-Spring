package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.ArticleReaction;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.ReactionType;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.mapper.ArticleReactionMapper;
import kg.nurtelecom.opinion.payload.article_reaction.ArticleReactionRequest;
import kg.nurtelecom.opinion.payload.article_reaction.ArticleReactionResponse;
import kg.nurtelecom.opinion.repository.ArticleReactionRepository;
import kg.nurtelecom.opinion.repository.ArticleRepository;
import kg.nurtelecom.opinion.service.ArticleReactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ArticleReactionServiceImpl implements ArticleReactionService {
    private final ArticleReactionRepository articleReactionRepository;
    private final ArticleRepository articleRepository;
    private final ArticleReactionMapper articleReactionMapper;

    public ArticleReactionServiceImpl(ArticleReactionRepository articleReactionRepository, ArticleRepository articleRepository, ArticleReactionMapper articleReactionMapper) {
        this.articleReactionRepository = articleReactionRepository;
        this.articleRepository = articleRepository;
        this.articleReactionMapper = articleReactionMapper;
    }

    @Override
    public ResponseEntity<String> addReactionToArticle(ArticleReactionRequest reaction, User user) {
        Article article = articleRepository.findById(reaction.articleId())
                .orElseThrow(() -> new NotFoundException("Статья с айди " + reaction.articleId() + " не найдена"));

        ArticleReaction articleReaction;

        if(!articleReactionRepository.existsByArticleAndUser(article, user)) {
            articleReaction = new ArticleReaction(reaction.reactionType(), article, user);
            articleReactionRepository.save(articleReaction);
            return ResponseEntity.ok("Реакция добавлена");
        }

        articleReaction = articleReactionRepository.findByArticleAndUser(article, user).get();

        if(articleReaction.getReactionType().equals(reaction.reactionType())) {
            articleReactionRepository.delete(articleReaction);
            return ResponseEntity.ok("Реакция удалена");
        }

        articleReaction.setReactionType(reaction.reactionType());
        articleReactionRepository.save(articleReaction);

        return ResponseEntity.ok("Реакция обновлена");
    }

    @Override
    public ResponseEntity<Page<ArticleReactionResponse>> getLikesByArticleId(Long articleId, Pageable pageable) {
        Page<ArticleReaction> articleReactions = articleReactionRepository.findAllByArticleIdAndReactionType(articleId, ReactionType.LIKE, pageable);
        Page<ArticleReactionResponse> articleReactionResponsePage = articleReactionMapper.toArticleReactionResponsePage(articleReactions);

        return ResponseEntity.ok(articleReactionResponsePage);
    }

    @Override
    public ResponseEntity<Page<ArticleReactionResponse>> getDislikesByArticleId(Long articleId, Pageable pageable) {
        Page<ArticleReaction> articleReactions = articleReactionRepository.findAllByArticleIdAndReactionType(articleId, ReactionType.DISLIKE, pageable);
        Page<ArticleReactionResponse> articleReactionResponsePage = articleReactionMapper.toArticleReactionResponsePage(articleReactions);

        return ResponseEntity.ok(articleReactionResponsePage);
    }


}
