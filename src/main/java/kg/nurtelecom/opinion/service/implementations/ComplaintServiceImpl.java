package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.Complaint;
import kg.nurtelecom.opinion.enums.ComplaintStatus;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.mapper.ComplaintMapper;
import kg.nurtelecom.opinion.payload.complaint.ComplaintDTO;
import kg.nurtelecom.opinion.repository.ArticleRepository;
import kg.nurtelecom.opinion.repository.ComplaintRepository;
import kg.nurtelecom.opinion.service.ComplaintService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository repository;
    private final ComplaintMapper mapper;
    private final ArticleRepository articleRepository;

    public ComplaintServiceImpl(ComplaintRepository repository, ComplaintMapper mapper, ArticleRepository articleRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.articleRepository = articleRepository;
    }

    @Override
    public ResponseEntity<ComplaintDTO> createComplaint(Long id, ComplaintDTO complaintDTO) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isEmpty()) {
            throw new NotFoundException("there is no such article.");
        }
        Complaint complaintEntity = mapper.toEntity(complaintDTO);
        complaintEntity.setStatus(ComplaintStatus.NEW);
        complaintEntity.setArticle(article.get());
        complaintEntity.setUser(article.get().getAuthor());
        complaintEntity.setReason(complaintDTO.reason());

        Complaint complaint = repository.save(complaintEntity);
        ComplaintDTO complainResponse = mapper.toModel(complaint);

        return new ResponseEntity<>(complainResponse, HttpStatus.CREATED);
    }
}
