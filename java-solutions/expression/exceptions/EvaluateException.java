package expression.exceptions;

public class EvaluateException extends RuntimeException {
    public EvaluateException(String message) {
        super(message);
    }

    public EvaluateException(String message, Exception cause) {
        super(message, cause);
    }
}
