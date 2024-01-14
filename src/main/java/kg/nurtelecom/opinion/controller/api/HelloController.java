package kg.nurtelecom.opinion.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class HelloController {

    @GetMapping
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello World from Bulletin board(Доска объявлений)");
    }
}
