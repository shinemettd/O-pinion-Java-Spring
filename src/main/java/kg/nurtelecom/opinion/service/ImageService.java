package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String loadImage(MultipartFile image);

    ResponseEntity<String> updateCoverImage(Long articleId, MultipartFile image, User user);

    ResponseEntity<Void> deleteImage(String imagePath);

    ResponseEntity<Void> deleteCoverImage(Long articleId,User user);

    ResponseEntity<Void> changeUserAvatar(User user, MultipartFile photo);

    Resource load(String path);
}
