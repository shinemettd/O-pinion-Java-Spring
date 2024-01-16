package kg.nurtelecom.opinion.controller.api;

import jakarta.validation.Valid;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.article.ArticleRequest;
import kg.nurtelecom.opinion.payload.article.ArticleResponse;
import kg.nurtelecom.opinion.payload.article.ArticleGetResponse;
import kg.nurtelecom.opinion.payload.article.ArticlesGetResponse;
import kg.nurtelecom.opinion.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<Page<ArticlesGetResponse>> getArticles(@PageableDefault(page = 0, size = 10, sort = "dateTime") Pageable pageable){
        System.out.println(pageable.getSort());
        return service.getArticles(pageable);
    }

    @GetMapping("/my-articles")
    public ResponseEntity<Page<ArticlesGetResponse>> getMyArticles(@PageableDefault(page = 0, size = 10, sort = "dateTime") Pageable pageable,
                                                                   @AuthenticationPrincipal User user) {
        System.out.println(pageable.getSort());
        return service.getMyArticles(user, pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponse> editArticle(@PathVariable("id") Long id,
                                                       @RequestBody @Valid ArticleRequest article) {
        return service.editArticle(article, id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleGetResponse> getArticle(@PathVariable("id") Long id) {

        return service.getArticle(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Long id) {

        return service.deleteArticle(id);
    }




}
