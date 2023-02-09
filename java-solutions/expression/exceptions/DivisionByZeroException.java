package expression.exceptions;

public class DivisionByZeroException extends EvaluateException {
    public DivisionByZeroException(String message) {
        super(message);
    }

    public DivisionByZeroException(String message, Exception cause) {
        super(message, cause);
    }
}
