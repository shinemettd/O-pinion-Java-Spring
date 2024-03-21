package kg.nurtelecom.opinion.payload.saved_announcement;

import kg.nurtelecom.opinion.payload.announcement.AnnouncementResponse;

public record SavedAnnouncementResponse(
        Long id,
        AnnouncementResponse announcement
) {
}
