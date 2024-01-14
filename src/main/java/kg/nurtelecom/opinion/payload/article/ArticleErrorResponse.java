package kg.nurtelecom.opinion.payload.article;

public class ArticleErrorResponse {
    private String message;

    public ArticleErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
