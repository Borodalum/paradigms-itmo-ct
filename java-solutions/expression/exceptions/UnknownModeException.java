package expression.exceptions;

public class UnknownModeException extends EvaluateException {

    public UnknownModeException(String message) {
        super(message);
    }
    public UnknownModeException(String message, Exception cause) {
        super(message, cause);
    }
    public UnknownModeException() {
        super("Unknown mode");
    }
}
