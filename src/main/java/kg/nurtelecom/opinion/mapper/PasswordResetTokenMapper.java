package kg.nurtelecom.opinion.mapper;

import kg.nurtelecom.opinion.entity.PasswordResetToken;
import kg.nurtelecom.opinion.payload.user.PasswordResetTokenResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PasswordResetTokenMapper {
    PasswordResetTokenResponse toModel(PasswordResetToken resetToken);
}
