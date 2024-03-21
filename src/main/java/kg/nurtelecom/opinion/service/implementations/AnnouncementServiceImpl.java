package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.Announcement;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.AccessType;
import kg.nurtelecom.opinion.enums.Role;
import kg.nurtelecom.opinion.exception.NoAccessException;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.payload.announcement.AnnouncementResponse;
import kg.nurtelecom.opinion.repository.AnnouncementRepository;
import kg.nurtelecom.opinion.service.AnnouncementService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.AccessException;
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
    public ResponseEntity<Page<AnnouncementResponse>> getAnnouncements(Pageable pageable, User user) {
        Role userRole = (user != null) ? user.getRole() : Role.ROLE_USER;
        Page<Announcement> announcements = announcementRepository.findAll(pageable);
        List<AnnouncementResponse> announcementsList = new ArrayList<>();
        announcements.forEach(announcement -> {
            if (!(userRole.equals(Role.ROLE_USER) && announcement.getAccessType().equals(AccessType.EMPLOYEES))) {
                AnnouncementResponse announcementResponse = new AnnouncementResponse(
                        announcement.getId(),
                        announcement.getTitle(),
                        announcement.getContent(),
                        announcement.getCoverImage(),
                        announcement.getDateTime(),
                        announcement.getViewsCount(),
                        announcement.getAccessType()
                );
                announcementsList.add(announcementResponse);
            }
        });

        Page<AnnouncementResponse> response = new PageImpl<>(announcementsList, pageable, announcements.getTotalElements());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AnnouncementResponse> getAnnouncement(Long id, User user) {
        Role userRole = (user != null) ? user.getRole() : Role.ROLE_USER;
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Объявления с таким id не существует "));

        if (userRole.equals(Role.ROLE_USER) && announcement.getAccessType().equals(AccessType.EMPLOYEES)) {
            throw new NoAccessException("У вас нет прав для просмотра этого объявления");
        }

        AnnouncementResponse response = new AnnouncementResponse(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getCoverImage(),
                announcement.getDateTime(),
                announcement.getViewsCount(),
                announcement.getAccessType()
        );

        return ResponseEntity.ok(response);
    }


}
