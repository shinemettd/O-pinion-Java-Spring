package kg.nurtelecom.opinion.controller.api;

import jakarta.validation.Valid;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.article.ArticleRequest;
import kg.nurtelecom.opinion.payload.article.ArticleResponse;
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
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody @Valid ArticleRequest article,
                                                         @AuthenticationPrincipal User user) {

        return service.createArticle(article, user);
    }

    @GetMapping
    public ResponseEntity<List<ArticleGetResponse>> getArticles() {

        return service.getArticles();
    }

    @GetMapping("/my-articles")
    public ResponseEntity<List<ArticleGetResponse>> getMyArticles(@AuthenticationPrincipal User user) {

        return service.getMyArticles(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponse> editArticle(@PathVariable("id") long id,
                                                       @RequestBody @Valid ArticleRequest article) {
        return service.editArticle(article, id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleGetResponse> getArticle(@PathVariable("id") long id) {

        return service.getArticle(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") long id) {

        return service.deleteArticle(id);
    }




}
