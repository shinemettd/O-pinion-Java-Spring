package kg.nurtelecom.opinion.mapper;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.user.GetUserResponse;
import kg.nurtelecom.opinion.payload.user.UserSignUpRequest;
import kg.nurtelecom.opinion.payload.user.UserSignUpResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserSignUpRequest user);

    UserSignUpResponse toModel(User user);

    GetUserResponse toGetUserResponse(User user);

}
