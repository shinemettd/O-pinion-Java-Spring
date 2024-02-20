package kg.nurtelecom.opinion.mapper;

import kg.nurtelecom.opinion.entity.AnnouncementComment;
import kg.nurtelecom.opinion.payload.announcement_comment.AnnouncementCommentRequest;
import kg.nurtelecom.opinion.payload.announcement_comment.AnnouncementCommentResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnnouncementCommentMapper {
    AnnouncementComment toEntity(AnnouncementCommentRequest announcementCommentRequest);

    AnnouncementCommentResponse toModel(AnnouncementComment announcementComment);
}
