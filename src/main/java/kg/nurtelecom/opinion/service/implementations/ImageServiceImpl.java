package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.ArticleStatus;
import kg.nurtelecom.opinion.exception.ImageDeletingException;
import kg.nurtelecom.opinion.exception.ImageSavingException;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.repository.ArticleRepository;
import kg.nurtelecom.opinion.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class ImageServiceImpl implements ImageService {

    private final ArticleRepository articleRepository;

    public ImageServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public String loadImage(MultipartFile image) {
        try {
            byte[] bytes = image.getBytes();
            String fileName = "photo_" + UUID.randomUUID() + ".jpeg";
            String imagePath = "/home/Intern_Labs_5_0/Galina_Kim/" + fileName;
            Path path = Paths.get(imagePath);
            Files.write(path, bytes);
            return imagePath;
        } catch (IOException e) {
            throw new ImageSavingException("Ошибка при попытке сохранить изображение");
        }

    }

    @Transactional
    @Override
    public ResponseEntity<String> updateCoverImage(Long articleId, MultipartFile image, User user, String path) {
        Optional<Article> article =  articleRepository.findByIdAndStatusNotIn(articleId, List.of(ArticleStatus.BLOCKED, ArticleStatus.DELETED));
        if(article.isEmpty()) {
            throw new NotFoundException("Статьи с таким id не существует");
        }
        Article articleEntity = article.get();
        // проверяем точно ли пользователь хочет добавить фото к своей статье
        if(articleEntity.getAuthor().getId() != user.getId()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // необходимо удалять прежнее фото если оно есть !!!!!
        if(path != null) {
            deleteImage(path);
        }
        String imagePath = loadImage(image);
        articleEntity.setCoverImage(imagePath);

        return new ResponseEntity<>(imagePath, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteImage(String filePath) {
        Path path = Paths.get(filePath);
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new ImageDeletingException("Ошибка при удалении файла ");
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Override
    public ResponseEntity<Void> deleteCoverImage(Long articleId, User user) {
        Optional<Article> article =  articleRepository.findByIdAndStatusNotIn(articleId, List.of(ArticleStatus.BLOCKED, ArticleStatus.DELETED));
        if(article.isEmpty()) {
            throw new NotFoundException("Статьи с таким id не существует");
        }
        Article articleEntity = article.get();
        // проверяем точно ли пользователь хочет удалить  фото у своей статьи
        if(articleEntity.getAuthor().getId() != user.getId()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String imagePath = articleEntity.getCoverImage();
        articleEntity.setCoverImage(null);
        return deleteImage(imagePath);
    }
}
