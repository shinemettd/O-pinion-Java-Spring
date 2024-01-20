package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Tag;
import kg.nurtelecom.opinion.mapper.TagMapper;
import kg.nurtelecom.opinion.payload.article.TagDTO;
import kg.nurtelecom.opinion.repository.TagRepository;
import kg.nurtelecom.opinion.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper mapper;

    public TagServiceImpl(TagRepository tagRepository, TagMapper mapper) {
        this.tagRepository = tagRepository;
        this.mapper = mapper;
    }


    public ResponseEntity<List<TagDTO>> getAll() {
        List<Tag> tags = tagRepository.findAll();
        return new ResponseEntity<>(mapper.toTagDto(tags), HttpStatus.OK);
    }
}
