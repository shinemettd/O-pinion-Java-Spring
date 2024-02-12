package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Announcement;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.payload.announcement.AnnouncementGetDTO;
import kg.nurtelecom.opinion.repository.AnnouncementRepository;
import kg.nurtelecom.opinion.service.AnnouncementService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @Override
    public ResponseEntity<Page<AnnouncementGetDTO>> getAnnouncements(Pageable pageable, User user) {
        Page<Announcement> announcements = announcementRepository.findAll(pageable);
        List<AnnouncementGetDTO> announcementsList = new ArrayList<>();
        announcements.forEach(announcement -> {
            AnnouncementGetDTO announcementGetDTO = new AnnouncementGetDTO(
                    announcement.getTitle(),
                    announcement.getContent(),
                    announcement.getCoverImage(),
                    announcement.getDateTime(),
                    announcement.getViewsCount(),
                    announcement.getAccessType()
            );
            announcementsList.add(announcementGetDTO);
        });

        Page<AnnouncementGetDTO> response = new PageImpl<>(announcementsList, pageable, announcements.getTotalElements());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AnnouncementGetDTO> getAnnouncement(Long id, User user) {
        Announcement announcement = isAnnouncementExist(id);

        AnnouncementGetDTO response = new AnnouncementGetDTO(
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getCoverImage(),
                announcement.getDateTime(),
                announcement.getViewsCount(),
                announcement.getAccessType()
        );

        return ResponseEntity.ok(response);
    }

    private Announcement isAnnouncementExist(Long announcementId) {
        Optional<Announcement> announcement = announcementRepository.findById(announcementId);
        if (announcement.isEmpty()) {
            throw new NotFoundException("Объявления с таким id не существует");
        }

        return announcement.get();
    }
}
