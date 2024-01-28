package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.payload.tag.TagResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TagService {
    ResponseEntity<List<TagResponse>> getAll();
}
