package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.*;
import kg.nurtelecom.opinion.enums.AccessType;
import kg.nurtelecom.opinion.enums.Role;
import kg.nurtelecom.opinion.exception.NoAccessException;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.mapper.SavedAnnouncementMapper;
import kg.nurtelecom.opinion.payload.saved_announcement.SavedAnnouncementResponse;
import kg.nurtelecom.opinion.repository.AnnouncementRepository;
import kg.nurtelecom.opinion.repository.SavedAnnouncementsRepository;
import kg.nurtelecom.opinion.service.SavedAnnouncementsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SavedAnnouncementsServiceImpl implements SavedAnnouncementsService {
    private final SavedAnnouncementsRepository savedAnnouncementsRepository;
    private final AnnouncementRepository announcementRepository;
    private final SavedAnnouncementMapper mapper;

    public SavedAnnouncementsServiceImpl(SavedAnnouncementsRepository savedAnnouncementsRepository, AnnouncementRepository announcementRepository, SavedAnnouncementMapper mapper) {
        this.savedAnnouncementsRepository = savedAnnouncementsRepository;
        this.announcementRepository = announcementRepository;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<Page<SavedAnnouncementResponse>> getSavedAnnouncements(User user, Pageable pageable) {
        Page<SavedAnnouncement> allUsersSavedAnnouncements = savedAnnouncementsRepository.findAllByUser(pageable, user);
        return new ResponseEntity<>(mapper.toResponsePage(allUsersSavedAnnouncements), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> addAnnouncementToSaved(Long announcement_id, User user) {
        Announcement announcement = announcementRepository.findById(announcement_id)
                .orElseThrow(() -> new NotFoundException("Объявления с таким id не существует"));

        if(announcement.getAccessType().equals(AccessType.EMPLOYEES) && user.getRole().equals(Role.ROLE_USER)) {
            throw new NoAccessException("У вас нет доступа к этому объявлению");
        }

        if (savedAnnouncementsRepository.existsByAnnouncementIdAndUserId(announcement_id, user.getId())) {
            savedAnnouncementsRepository.deleteByAnnouncementIdAndUserId(announcement_id, user.getId());
        } else {
           SavedAnnouncement savedAnnouncement = new SavedAnnouncement(announcement, user);
           savedAnnouncementsRepository.save(savedAnnouncement);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> deleteAnnouncementFromSaved(Long announcement_id, User user) {
       if(!savedAnnouncementsRepository.existsByAnnouncementIdAndUserId(announcement_id, user.getId())){
           throw new NotFoundException("Объявление не добавлено в избранное");
       }
       savedAnnouncementsRepository.deleteByAnnouncementIdAndUserId(announcement_id, user.getId());
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
