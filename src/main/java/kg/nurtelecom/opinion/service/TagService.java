package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.tag.TagRequest;
import kg.nurtelecom.opinion.payload.tag.TagResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface TagService {
    ResponseEntity<Page<TagResponse>> getAll(Pageable pageable);

    ResponseEntity<TagResponse> createTag(TagRequest tagRequest);

    ResponseEntity<Page<TagResponse>> findTagsByName(@RequestParam("name") String tagName, Pageable pageable);
}
