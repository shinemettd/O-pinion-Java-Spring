package kg.nurtelecom.opinion.payload.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import kg.nurtelecom.opinion.enums.Gender;

import java.util.Date;

public record UserSignUpResponse(
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        String nickname,
        String email,
        Gender gender,
        String country,
        @JsonProperty("birth_date")
        Date birthDate
) {
}
