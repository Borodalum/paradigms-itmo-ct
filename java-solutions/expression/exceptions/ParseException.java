package expression.exceptions;

public class ParseException extends RuntimeException {
    public ParseException(String message) {
        super(message);
    }
    public ParseException(String message, Exception cause) {
        super(message, cause);
    }
}
