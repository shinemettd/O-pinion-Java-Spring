package kg.nurtelecom.opinion.payload.user;

import kg.nurtelecom.opinion.enums.Gender;

import java.util.Date;

public record UserSignUpResponse(
        String first_name,
        String last_name,
        String nickname,
        String email,
        Gender gender,
        String country,
        Date birth_date
) {
}
