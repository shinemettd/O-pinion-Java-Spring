package kg.nurtelecom.opinion.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String loadImage(MultipartFile image);
}
