package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.Complain;
import kg.nurtelecom.opinion.enums.ComplainStatus;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.mapper.ComplainMapper;
import kg.nurtelecom.opinion.payload.complain.ComplainDTO;
import kg.nurtelecom.opinion.repository.ArticleRepository;
import kg.nurtelecom.opinion.repository.ComplainRepository;
import kg.nurtelecom.opinion.service.ComplainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ComplainServiceImpl implements ComplainService {

    private final ComplainRepository repository;
    private final ComplainMapper mapper;
    private final ArticleRepository articleRepository;

    public ComplainServiceImpl(ComplainRepository repository, ComplainMapper mapper, ArticleRepository articleRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.articleRepository = articleRepository;
    }

    @Override
    public ResponseEntity<ComplainDTO> createComplain(Long id, ComplainDTO complainDTO) {
        Optional<Article> article = articleRepository.findById(id);
        if(article.isEmpty()) {
            throw new NotFoundException("there is no such article.");
        }
        Complain complainEntity = mapper.toComplain(complainDTO);
        complainEntity.setStatus(ComplainStatus.ON_CONSIDERATION);
        complainEntity.setArticleId(article.get());
        complainEntity.setUserId(article.get().getAuthor());
        complainEntity.setReason(complainDTO.reason());

        Complain complain = repository.save(complainEntity);
        ComplainDTO complainResponse = mapper.toComplainDTO(complain);

        return new ResponseEntity<>(complainResponse, HttpStatus.CREATED);
    }
}
