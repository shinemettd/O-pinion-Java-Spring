package kg.nurtelecom.opinion.payload.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import kg.nurtelecom.opinion.config.JsonDynamicUserSerializer;

import java.util.Date;

@JsonSerialize(using = JsonDynamicUserSerializer.class)
public record GetUserResponse(
        Long id,
        String firstName,
        String lastName,
        String nickname,
        String email,
        String avatar,
        @JsonProperty("birth_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        @Schema(type = "string", format = "date", example = "2000-01-21")
        Date birthDate
) {
}
