package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.repository.ArticleRepository;
import kg.nurtelecom.opinion.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
}
