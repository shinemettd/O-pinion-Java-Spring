package kg.nurtelecom.opinion.exception;

public class ExceedsNestingLevelException extends RuntimeException {
    public ExceedsNestingLevelException(String message) {
        super(message);
    }
}
