package kg.nurtelecom.opinion.controller.api;

import jakarta.validation.Valid;
import kg.nurtelecom.opinion.exception.ArticleNotCreatedException;
import kg.nurtelecom.opinion.payload.article.ArticleCreateRequest;
import kg.nurtelecom.opinion.payload.article.ArticleCreateResponse;
import kg.nurtelecom.opinion.payload.article.ArticleErrorResponse;
import kg.nurtelecom.opinion.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    public ResponseEntity<ArticleCreateResponse> createArticle(@RequestBody @Valid ArticleCreateRequest article,
                                                               BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder message = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors) {
                message.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new ArticleNotCreatedException(message.toString());
        }
        return service.createArticle(article);
    }

    @ExceptionHandler
    private ResponseEntity<ArticleErrorResponse> handleException(ArticleNotCreatedException exc) {
        ArticleErrorResponse response = new ArticleErrorResponse(exc.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
