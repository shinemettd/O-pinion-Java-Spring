package kg.nurtelecom.opinion.mapper;

import kg.nurtelecom.opinion.entity.SavedAnnouncement;
import kg.nurtelecom.opinion.entity.SavedArticle;
import kg.nurtelecom.opinion.payload.announcement.AnnouncementResponse;
import kg.nurtelecom.opinion.payload.saved_announcement.SavedAnnouncementResponse;
import kg.nurtelecom.opinion.payload.saved_article.SavedArticleResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface SavedAnnouncementMapper {

    SavedAnnouncementResponse toSavedAnnouncementResponse(SavedAnnouncement announcements);

    default Page<SavedAnnouncementResponse> toResponsePage(Page<SavedAnnouncement> savedArticles) {
        return savedArticles.map(this::toSavedAnnouncementResponse);
    }
}
