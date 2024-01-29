package kg.nurtelecom.opinion.payload.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserUpdateEmailResponse (
        String email,
        @JsonProperty("access_token")
        String accessToken
){
}
