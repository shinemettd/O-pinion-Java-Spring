package kg.nurtelecom.opinion.service.implementations;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.ArticleStatus;
import kg.nurtelecom.opinion.enums.Status;
import kg.nurtelecom.opinion.exception.*;
import kg.nurtelecom.opinion.repository.ArticleRepository;
import kg.nurtelecom.opinion.repository.UserRepository;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional
@Service
public class ImageServiceImpl implements ImageService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final Cloudinary cloudinary;

    public ImageServiceImpl(ArticleRepository articleRepository, UserRepository userRepository, Cloudinary cloudinary) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.cloudinary = cloudinary;
    }

    @Override
    public String loadImage(MultipartFile image) {
        try {
            byte[] bytes = image.getBytes();
            Map uploadResult = cloudinary.uploader().upload(bytes, ObjectUtils.emptyMap());

            return (String) uploadResult.get("url");
        } catch (IOException e) {
            throw new FileException("Ошибка при попытке загрузить изображение на Cloudinary");
        }
    }

    @Transactional
    @Override
    public ResponseEntity<String> updateCoverImage(Long articleId, MultipartFile image, User user) {
        Optional<Article> article =  articleRepository.findByIdAndStatusNotIn(articleId, List.of(ArticleStatus.BLOCKED, ArticleStatus.DELETED));
        Article articleEntity = article.orElseThrow(() ->  new NotFoundException("Статьи с таким id не существует"));

        if(!articleEntity.getAuthor().getId().equals(user.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String path = articleEntity.getCoverImage();
        if(path != null) {
            deleteImage(path);
        }
        String imagePath = loadImage(image);
        articleEntity.setCoverImage(imagePath);

        return new ResponseEntity<>(imagePath, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Void> deleteImage(String imagePath) {
        String publicId  = getImageKey(imagePath);
        if(publicId == null) {
            throw new RuntimeException("В пути до картинки отсутствует public id");
        }
        try {
            // Удаление изображения из Cloudinary по его public_id
            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting image from Cloudinary", e);
        }
    }

    private String getImageKey(String imagePath) {
        int lastSlashIndex = imagePath.lastIndexOf("/");

        int extensionDotIndex = imagePath.lastIndexOf(".");

        if (lastSlashIndex != -1 && extensionDotIndex != -1) {

            return imagePath.substring(lastSlashIndex + 1, extensionDotIndex);
        } else {
            return null;
        }

    }


    @Override
    public ResponseEntity<Void> deleteCoverImage(Long articleId, User user) {
        Optional<Article> article =  articleRepository.findByIdAndStatusNotIn(articleId, List.of(ArticleStatus.BLOCKED, ArticleStatus.DELETED));
        if(article.isEmpty()) {
            throw new NotFoundException("Статьи с таким id не существует");
        }
        Article articleEntity = article.get();
        // проверяем точно ли пользователь хочет удалить  фото у своей статьи
        if(articleEntity.getAuthor().getId().equals(user.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String imagePath = articleEntity.getCoverImage();
        articleEntity.setCoverImage(null);
        return deleteImage(imagePath);
    }

    @Override
    public ResponseEntity<Void> changeUserAvatar(User user, MultipartFile photo) {
        if(photo.isEmpty()) {
            throw new FileException("Файл пустой");
        }
        if(user.getStatus() == Status.DELETED) {
            throw new NotFoundException("Вы удалили свой аккаунт");
        }
        // deleting previous avatar
        User userEntity = userRepository.findById(user.getId()).get();
        String previousAvatar = userEntity.getAvatar();
        if(previousAvatar != null) {
            deleteImage(previousAvatar);
        }
        userEntity.setAvatar(loadImage(photo));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
