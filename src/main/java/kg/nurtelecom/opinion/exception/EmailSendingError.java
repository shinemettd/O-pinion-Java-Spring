package kg.nurtelecom.opinion.exception;

public class EmailSendingError extends RuntimeException {
    public EmailSendingError(String message) {
        super(message);
    }
}
