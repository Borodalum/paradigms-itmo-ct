package expression.exceptions;

public class OverflowException extends EvaluateException {
    public OverflowException(String message) {
        super(message);
    }
    public OverflowException(String message, Exception cause) {
        super(message, cause);
    }
}
