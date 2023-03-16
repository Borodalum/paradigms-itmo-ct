package expression.exceptions;

public class IllegalConstantExcpetion extends ParseException {
    public IllegalConstantExcpetion(String message) {
        super(message);
    }
    public IllegalConstantExcpetion(String message, Exception cause) {
        super(message, cause);
    }

}
