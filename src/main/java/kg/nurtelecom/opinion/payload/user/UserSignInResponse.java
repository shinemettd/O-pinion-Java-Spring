package kg.nurtelecom.opinion.payload.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserSignInResponse(
        @JsonProperty("access_token")
        String accessToken
) {
}
