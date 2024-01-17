package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.article_reaction.ArticleReactionRequest;
import kg.nurtelecom.opinion.payload.article_reaction.ArticleReactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ArticleReactionService {
    ResponseEntity<String> addReactionToArticle(ArticleReactionRequest reaction, User user);

    ResponseEntity<Page<ArticleReactionResponse>> getLikesByArticleId(Long articleId, Pageable pageable);

    ResponseEntity<Page<ArticleReactionResponse>> getDislikesByArticleId(Long articleId, Pageable pageable);
}
