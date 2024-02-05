package kg.nurtelecom.opinion.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import kg.nurtelecom.opinion.payload.user.GetUserResponse;
import java.io.IOException;

public class JsonDynamicUserSerializer extends JsonSerializer<GetUserResponse> {

    public JsonDynamicUserSerializer() {}

    @Override
    public void serialize(GetUserResponse userResponse, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        gen.writeStartObject();
        gen.writeObjectField("id", userResponse.id());
        gen.writeObjectField("avatar", userResponse.avatar());


        if (userResponse.firstName() != null) {
            gen.writeObjectField("firstName", userResponse.firstName());
        }

        if (userResponse.lastName() != null) {
            gen.writeObjectField("lastName", userResponse.lastName());
        }
        if (userResponse.email() != null) {
            gen.writeObjectField("email", userResponse.email());
        }
        if (userResponse.birthDate() != null) {
            gen.writeObjectField("birthDate", userResponse.birthDate());
        }
        gen.writeEndObject();

    }
}
