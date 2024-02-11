package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.ArticleStatus;
import kg.nurtelecom.opinion.enums.Status;
import kg.nurtelecom.opinion.exception.FileException;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.repository.ArticleRepository;
import kg.nurtelecom.opinion.repository.UserRepository;
import kg.nurtelecom.opinion.service.ImageService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class ImageServiceImpl implements ImageService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ImageServiceImpl(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String loadArticleImage(MultipartFile image) {
        if (!image.getContentType().toLowerCase().startsWith("image/")) {
            throw new FileException("Формат изображения не поддерживается");
        }

        try {
            String fileName = "image_" + UUID.randomUUID();
            String imagePath = "/home/opinion/opinion-front/images/articles_images/";
            Path uploadPath = Paths.get(imagePath);

            File destFile = new File(uploadPath.toFile(), fileName);
            Thumbnails.of(image.getInputStream())
                    .scale(1)
                    .outputQuality(0.5)
                    .toFile(destFile);
            return destFile.getAbsolutePath() + "." + getFileExtension(image);
        } catch (IOException e) {
            throw new FileException("Ошибка при сохранении изображения");
        }
    }

    @Override
    public String loadUserImage(MultipartFile image) {
        try {
            byte[] bytes = image.getBytes();
            String fileName = "photo_" + UUID.randomUUID() + ".jpeg";
            String imagePath = "/home/opinion/opinion-front/images/users_images/" + fileName;
            Path path = Paths.get(imagePath);
            Files.write(path, bytes);
            return imagePath;
        } catch (IOException e) {
            throw new FileException("Ошибка при попытке сохранить изображение");
        }

    }

    @Transactional
    @Override
    public ResponseEntity<String> updateCoverImage(Long articleId, MultipartFile image, User user) {
        Optional<Article> article = articleRepository.findByIdAndStatusNotIn(articleId, List.of(ArticleStatus.BLOCKED, ArticleStatus.DELETED));
        Article articleEntity = article.orElseThrow(() -> new NotFoundException("Статьи с таким id не существует"));

        if (!articleEntity.getAuthor().getId().equals(user.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String path = articleEntity.getCoverImage();
        if (path != null) {
            deleteImage(path);
        }
        String imagePath = loadArticleImage(image);
        articleEntity.setCoverImage(imagePath);

        return new ResponseEntity<>(imagePath, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Void> deleteImage(String imagePath) {
        Path path = Paths.get(imagePath);
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new FileException("Ошибка при удалении файла ");
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


    public ResponseEntity<Void> deleteCoverImage(Long articleId, User user) {
        Optional<Article> article = articleRepository.findByIdAndStatusNotIn(articleId, List.of(ArticleStatus.BLOCKED, ArticleStatus.DELETED));
        if (article.isEmpty()) {
            throw new NotFoundException("Статьи с таким id не существует");
        }
        Article articleEntity = article.get();
        // проверяем точно ли пользователь хочет удалить  фото у своей статьи
        if (articleEntity.getAuthor().getId().equals(user.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String imagePath = articleEntity.getCoverImage();
        articleEntity.setCoverImage(null);
        return deleteImage(imagePath);
    }

    @Override
    public ResponseEntity<Void> changeUserAvatar(User user, MultipartFile photo) {
        if (photo.isEmpty()) {
            throw new FileException("Файл пустой");
        }
        if (user.getStatus() == Status.DELETED) {
            throw new NotFoundException("Вы удалили свой аккаунт");
        }
        // deleting previous avatar
        User userEntity = userRepository.findById(user.getId()).get();
        String previousAvatar = userEntity.getAvatar();
        if (previousAvatar != null) {
            deleteImage(previousAvatar);
        }
        userEntity.setAvatar(loadUserImage(photo));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String getFileExtension(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        return originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
    }
}
