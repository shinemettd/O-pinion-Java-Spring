package kg.nurtelecom.opinion.exception;

public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException(String msg) {
        super(msg);
    }
}
