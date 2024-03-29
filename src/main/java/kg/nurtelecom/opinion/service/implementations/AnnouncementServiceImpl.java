package kg.nurtelecom.opinion.service.implementations;

import jakarta.transaction.Transactional;
import kg.nurtelecom.opinion.entity.Announcement;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.enums.AccessType;
import kg.nurtelecom.opinion.enums.Role;
import kg.nurtelecom.opinion.enums.SourceType;
import kg.nurtelecom.opinion.exception.NoAccessException;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.payload.announcement.AnnouncementResponse;
import kg.nurtelecom.opinion.repository.AnnouncementCommentRepository;
import kg.nurtelecom.opinion.repository.AnnouncementRepository;
import kg.nurtelecom.opinion.repository.SavedAnnouncementsRepository;
import kg.nurtelecom.opinion.service.AnnouncementService;
import kg.nurtelecom.opinion.service.MailSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final SavedAnnouncementsRepository savedAnnouncementsRepository;
    private final AnnouncementCommentRepository announcementCommentRepository;
    private final MailSenderService mailSenderService;
    @Value("${client-application.host}")
    private String clientHost;
    @Value("${client-application.route.announcement}")
    private String clientAnnouncementRoute;

    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository, SavedAnnouncementsRepository savedAnnouncementsRepository, AnnouncementCommentRepository announcementCommentRepository, MailSenderService mailSenderService) {
        this.announcementRepository = announcementRepository;
        this.savedAnnouncementsRepository = savedAnnouncementsRepository;

        this.announcementCommentRepository = announcementCommentRepository;
        this.mailSenderService = mailSenderService;
    }

    @Override
    public ResponseEntity<Page<AnnouncementResponse>> getAnnouncements(Pageable pageable, User user) {
        Role userRole = (user != null) ? user.getRole() : Role.ROLE_USER;
        Page<Announcement> announcements = announcementRepository.findAll(pageable);
        List<AnnouncementResponse> announcementsList = new ArrayList<>();
        announcements.forEach(announcement -> {
            if (!(userRole.equals(Role.ROLE_USER) && announcement.getAccessType().equals(AccessType.EMPLOYEES))) {
                boolean isInFavourites = (user != null) ?
                        savedAnnouncementsRepository.existsByAnnouncementIdAndUserId(announcement.getId(), user.getId())
                        : false;
                AnnouncementResponse announcementResponse = new AnnouncementResponse(
                        announcement.getId(),
                        announcement.getTitle(),
                        announcement.getContent(),
                        announcement.getCoverImage(),
                        announcement.getDateTime(),
                        announcement.getViewsCount(),
                        savedAnnouncementsRepository.countByAnnouncementId(announcement.getId()),
                        announcementCommentRepository.countByAnnouncementId(announcement.getId()),
                        isInFavourites,
                        announcement.getAccessType()
                );
                announcementsList.add(announcementResponse);
            }
        });

        Page<AnnouncementResponse> response = new PageImpl<>(announcementsList, pageable, announcements.getTotalElements());

        return ResponseEntity.ok(response);
    }


    @Override
    @Transactional
    public ResponseEntity<AnnouncementResponse> getAnnouncement(Long id, User user) {
        Role userRole = (user != null) ? user.getRole() : Role.ROLE_USER;
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Объявления с таким id не существует "));

        if (userRole.equals(Role.ROLE_USER) && announcement.getAccessType().equals(AccessType.EMPLOYEES)) {
            throw new NoAccessException("У вас нет прав для просмотра этого объявления");
        }

        announcementRepository.incrementViewsCount(id);

        boolean isInFavourites = (user != null) ?
                savedAnnouncementsRepository.existsByAnnouncementIdAndUserId(announcement.getId(), user.getId())
                : false;
        AnnouncementResponse response = new AnnouncementResponse(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getCoverImage(),
                announcement.getDateTime(),
                announcement.getViewsCount(),
                savedAnnouncementsRepository.countByAnnouncementId(announcement.getId()),
                announcementCommentRepository.countByAnnouncementId(announcement.getId()),
                isInFavourites,
                announcement.getAccessType()
        );

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<String> shareAnnouncement(Long id, String shareType) {
        if(announcementRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Объявления  с таким id не существует");
        }
        String announcementUrl = "http://" + clientHost + clientAnnouncementRoute + "/" + id;
        switch (shareType){
            case("announcement"):
                return new ResponseEntity<>(announcementUrl,  HttpStatus.OK);
            case("telegram"):
                return new ResponseEntity<>("https://t.me/share/url?url=" + announcementUrl, HttpStatus.OK);
            case("whatsapp"):
                return new ResponseEntity<>("https://web.whatsapp.com/send?text=" + announcementUrl, HttpStatus.OK);
            case("vk"):
                return new ResponseEntity<>("https://vk.com/share.php?url=" + announcementUrl, HttpStatus.OK);
            default:
                return new ResponseEntity<>("Share type not found", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Void> shareAnnouncementByEmail(Long id, String recipient, String from) {
        if(announcementRepository.findById(id).isPresent()) {
            String announcementUrl = "http://" + clientHost + clientAnnouncementRoute + "/" + id;

            mailSenderService.sendEmail(recipient, announcementUrl, from, SourceType.ANNOUNCEMENT);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        throw new NotFoundException("Объявления с таким id не существует");
    }
}
