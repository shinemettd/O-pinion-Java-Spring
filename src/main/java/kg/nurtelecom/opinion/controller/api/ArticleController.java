package kg.nurtelecom.opinion.controller.api;

import jakarta.validation.Valid;
import kg.nurtelecom.opinion.exception.ArticleNotCreatedException;
import kg.nurtelecom.opinion.payload.article.ArticleCreateRequest;
import kg.nurtelecom.opinion.payload.article.ArticleCreateResponse;
import kg.nurtelecom.opinion.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    public ResponseEntity<ArticleCreateResponse> createArticle(@RequestBody @Valid ArticleCreateRequest article) {
        return service.createArticle(article);
    }

}
