package kg.nurtelecom.opinion.service.implementations;


import kg.nurtelecom.opinion.entity.Tag;

import kg.nurtelecom.opinion.enums.ArticleStatus;
import kg.nurtelecom.opinion.enums.TagStatus;
import kg.nurtelecom.opinion.mapper.TagMapper;
import kg.nurtelecom.opinion.payload.tag.TagRequest;
import kg.nurtelecom.opinion.payload.tag.TagResponse;
import kg.nurtelecom.opinion.repository.TagRepository;
import kg.nurtelecom.opinion.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper mapper;

    public TagServiceImpl(TagRepository tagRepository, TagMapper mapper) {
        this.tagRepository = tagRepository;
        this.mapper = mapper;
    }



    @Override
    public ResponseEntity<Page<TagResponse>> getAll(Pageable pageable) {
        Page<Tag> tags = tagRepository.findByStatus(TagStatus.APPROVED, pageable);
        return new ResponseEntity<>(mapper.toTagDto(tags), HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<TagResponse> createTag(TagRequest tagRequest) {
        Optional<Tag> tag = tagRepository.findByName(tagRequest.name());
        if(tag.isPresent()) {
            // if such tag is already exist we return it
            Tag tagEntity = tag.get();
            return new ResponseEntity<>(mapper.toTagResponse(tagEntity), HttpStatus.OK);
        }
        // if no such tag we create and return it 
        Tag tagEntity = mapper.toTagEntity(tagRequest);
        tagEntity.setStatus(TagStatus.ON_MODERATION);
        tagRepository.save(tagEntity);
        return new ResponseEntity<>(mapper.toTagResponse(tagEntity),HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Page<TagResponse>> findTagsByName(String tagName, Pageable pageable) {
        Page<Tag> foundTags = tagRepository.findByNameContaining(tagName, pageable);
        return new ResponseEntity<>(mapper.toTagDto(foundTags), HttpStatus.OK);
    }
}
