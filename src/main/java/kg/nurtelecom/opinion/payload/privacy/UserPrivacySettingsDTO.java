package kg.nurtelecom.opinion.payload.privacy;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserPrivacySettingsDTO(
        @JsonProperty("is_first_name_visible")
        boolean isFirstNameVisible,
        @JsonProperty("is_last_name_visible")
        boolean isLastNameVisible,
        @JsonProperty("is_email_visible")
        boolean isEmailVisible,
        @JsonProperty("is_birth_date_visible")
        boolean isBirthDateVisible
) {
}
