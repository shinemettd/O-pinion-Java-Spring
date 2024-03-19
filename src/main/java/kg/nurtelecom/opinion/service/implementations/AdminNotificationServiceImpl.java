package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.AdminNotification;
import kg.nurtelecom.opinion.repository.AdminNotificationRepository;
import kg.nurtelecom.opinion.service.AdminNotificationService;
import org.springframework.stereotype.Service;

@Service
public class AdminNotificationServiceImpl implements AdminNotificationService {
    private final AdminNotificationRepository adminNotificationRepository;

    public AdminNotificationServiceImpl(AdminNotificationRepository adminNotificationRepository) {
        this.adminNotificationRepository = adminNotificationRepository;
    }

    @Override
    public void createAdminNotification(String content) {
        AdminNotification adminNotification = new AdminNotification("Жалоба", content, false);
        adminNotificationRepository.save(adminNotification);
    }
}
