package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.payload.article.TagDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TagService {
    ResponseEntity<List<TagDTO>> getAll();
}
