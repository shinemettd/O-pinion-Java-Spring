package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Article;
import kg.nurtelecom.opinion.exception.ImageSavingException;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public String loadImage(MultipartFile image) {
        try {
            byte[] bytes = image.getBytes();
            String fileName = "photo_" + UUID.randomUUID() + "_" + image.getOriginalFilename();
            String imagePath = "C:/opinion-pictures/" + fileName;
            Path path = Paths.get(imagePath);
            Files.write(path, bytes);
            return imagePath;
        } catch (IOException e) {
            throw new ImageSavingException("Ошибка при попытке сохранить изображение");
        }

    }
}
