package kg.nurtelecom.opinion.payload.user;

public record UserSignInRequest(
        String email,
        String password
) {
}
