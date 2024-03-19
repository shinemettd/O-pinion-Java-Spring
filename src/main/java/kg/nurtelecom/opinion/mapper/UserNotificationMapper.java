package kg.nurtelecom.opinion.mapper;

import kg.nurtelecom.opinion.entity.Tag;
import kg.nurtelecom.opinion.entity.UserNotification;
import kg.nurtelecom.opinion.payload.notification.UserNotificationResponse;
import kg.nurtelecom.opinion.payload.tag.TagDTO;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface UserNotificationMapper {
    UserNotificationResponse toUserNotificationResponse(UserNotification userNotification);

    default Page<UserNotificationResponse> toUserNotificationResponsePage(Page<UserNotification> userNotificationPage) {
        return userNotificationPage.map(this::toUserNotificationResponse);
    }
}
