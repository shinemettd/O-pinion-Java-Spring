package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.Complaint;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.ComplaintStatus;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.mapper.ComplaintMapper;
import kg.nurtelecom.opinion.payload.complaint.ComplaintDTO;
import kg.nurtelecom.opinion.repository.ArticleRepository;
import kg.nurtelecom.opinion.repository.ComplaintRepository;
import kg.nurtelecom.opinion.service.AdminNotificationService;
import kg.nurtelecom.opinion.service.ComplaintService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository repository;
    private final ComplaintMapper mapper;
    private final ArticleRepository articleRepository;
    private final AdminNotificationService adminNotificationService;
    @Value("${admin-panel.host}")
    private String host;
    @Value("${admin-panel.route.article}")
    private String articleRoute;
    @Value("${admin-panel.route.user}")
    private String userRoute;

    public ComplaintServiceImpl(ComplaintRepository repository, ComplaintMapper mapper, ArticleRepository articleRepository, AdminNotificationService adminNotificationService) {
        this.repository = repository;
        this.mapper = mapper;
        this.articleRepository = articleRepository;
        this.adminNotificationService = adminNotificationService;
    }

    @Override
    public ResponseEntity<ComplaintDTO> createComplaint(Long id, ComplaintDTO complaintDTO, User user) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Статья с таким айди не найдена"));

        Complaint complaintEntity = mapper.toEntity(complaintDTO);
        complaintEntity.setStatus(ComplaintStatus.NEW);
        complaintEntity.setArticle(article);
        complaintEntity.setUser(user);

        Complaint complaint = repository.save(complaintEntity);
        ComplaintDTO complainResponse = mapper.toModel(complaint);

        String content = constructAdminNotification(id, host, user);
        adminNotificationService.createAdminNotification("Жалоба", content);

        return new ResponseEntity<>(complainResponse, HttpStatus.CREATED);
    }

    private String constructAdminNotification(Long articleId, String host, User user) {
        String content = "<p>Пользователь <a href=\"[[user_url]]\">[[nickname]]</a> пожаловался на <a href=\"[[article_url]]\">статью</a>." +
                "<br>Кликните по ссылке, чтобы перейти к статье.</p>";
        content = content.replace("[[user_url]]", "http://" + host + userRoute + "/" + user.getNickname());
        content = content.replace("[[nickname]]", user.getNickname());
        content = content.replace("[[article_url]]", "http://" + host + articleRoute + "/" + articleId);
        return content;
    }
}
