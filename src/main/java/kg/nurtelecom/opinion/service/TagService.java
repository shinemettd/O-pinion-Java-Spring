package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.payload.tag.TagRequest;
import kg.nurtelecom.opinion.payload.tag.TagDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface TagService {
    ResponseEntity<Page<TagDTO>> getAll(Pageable pageable);

    ResponseEntity<TagDTO> createTag(TagRequest tagRequest);

    ResponseEntity<Page<TagDTO>> findTagsByName(@RequestParam("name") String tagName, Pageable pageable);
}
