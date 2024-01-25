package kg.nurtelecom.opinion.payload.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public class GetUserProfileDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String nickname;
    private String email;
    private String avatar;
    @JsonProperty("birth_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(type = "string", format = "date", example = "2000-01-21")
    private Date birthDate;

    @JsonProperty("is_first_name_visible")
    private boolean isFirstNameVisible;
    @JsonProperty("is_last_name_visible")
    private boolean isLastNameVisible;
    @JsonProperty("is_email_visible")
    private boolean isEmailVisible;

    @JsonProperty("is_birth_date_visible")
    private boolean isBirthDateVisible;

    public GetUserProfileDTO(Long id, String firstName, String lastName, String nickname, String email, String avatar, Date birthDate, boolean isFirstNameVisible, boolean isLastNameVisible, boolean isEmailVisible, boolean isBirthDateVisible) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.email = email;
        this.avatar = avatar;
        this.birthDate = birthDate;
        this.isFirstNameVisible = isFirstNameVisible;
        this.isLastNameVisible = isLastNameVisible;
        this.isEmailVisible = isEmailVisible;
        this.isBirthDateVisible = isBirthDateVisible;
    }
}
