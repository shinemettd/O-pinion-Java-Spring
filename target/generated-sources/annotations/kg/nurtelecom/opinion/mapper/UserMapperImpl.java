package kg.nurtelecom.opinion.mapper;

import java.time.ZoneOffset;
import java.util.Date;
import javax.annotation.processing.Generated;
import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.user.GetUserResponse;
import kg.nurtelecom.opinion.payload.user.UserResponse;
import kg.nurtelecom.opinion.payload.user.UserSignUpRequest;
import kg.nurtelecom.opinion.payload.user.UserSignUpResponse;
import kg.nurtelecom.opinion.payload.user.UserUpdateEmailRequest;
import kg.nurtelecom.opinion.payload.user.UserUpdateRequest;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-16T20:00:07+0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserSignUpRequest user) {
        if ( user == null ) {
            return null;
        }

        User user1 = new User();

        user1.setFirstName( user.firstName() );
        user1.setLastName( user.lastName() );
        user1.setNickname( user.nickname() );
        user1.setEmail( user.email() );
        user1.setPassword( user.password() );
        if ( user.birthDate() != null ) {
            user1.setBirthDate( Date.from( user.birthDate().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }

        return user1;
    }

    @Override
    public UserSignUpResponse toModel(User user) {
        if ( user == null ) {
            return null;
        }

        String firstName = null;
        String lastName = null;
        String nickname = null;
        String email = null;
        Date birthDate = null;

        firstName = user.getFirstName();
        lastName = user.getLastName();
        nickname = user.getNickname();
        email = user.getEmail();
        birthDate = user.getBirthDate();

        UserSignUpResponse userSignUpResponse = new UserSignUpResponse( firstName, lastName, nickname, email, birthDate );

        return userSignUpResponse;
    }

    @Override
    public GetUserResponse toGetUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        Long id = null;
        String firstName = null;
        String lastName = null;
        String nickname = null;
        String email = null;
        String avatar = null;
        Date birthDate = null;

        id = user.getId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        nickname = user.getNickname();
        email = user.getEmail();
        avatar = user.getAvatar();
        birthDate = user.getBirthDate();

        GetUserResponse getUserResponse = new GetUserResponse( id, firstName, lastName, nickname, email, avatar, birthDate );

        return getUserResponse;
    }

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        Long id = null;
        String nickname = null;
        String avatar = null;

        id = user.getId();
        nickname = user.getNickname();
        avatar = user.getAvatar();

        UserResponse userResponse = new UserResponse( id, nickname, avatar );

        return userResponse;
    }

    @Override
    public User toUser(UserUpdateRequest user) {
        if ( user == null ) {
            return null;
        }

        User user1 = new User();

        user1.setFirstName( user.firstName() );
        user1.setLastName( user.lastName() );
        user1.setNickname( user.nickname() );
        user1.setBirthDate( user.birthDate() );

        return user1;
    }

    @Override
    public User toUserEntity(UserUpdateEmailRequest user) {
        if ( user == null ) {
            return null;
        }

        User user1 = new User();

        user1.setEmail( user.email() );

        return user1;
    }
}
