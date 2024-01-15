package kg.nurtelecom.opinion.controller.api;

import jakarta.validation.Valid;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.article.ArticleCreateRequest;
import kg.nurtelecom.opinion.payload.article.ArticleCreateResponse;
import kg.nurtelecom.opinion.payload.article.ArticleGetResponse;
import kg.nurtelecom.opinion.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "*")
public class ArticleController {

    private final ArticleService service;

    @Autowired
    public ArticleController(ArticleService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<ArticleCreateResponse> createArticle(@RequestBody @Valid ArticleCreateRequest article,
                                                               @AuthenticationPrincipal User user) {

        return service.createArticle(article, user);
    }

    @GetMapping
    public ResponseEntity<List<ArticleGetResponse>> getArticles() {
        return service.getArticles();
    }


}
