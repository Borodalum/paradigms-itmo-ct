package expression.exceptions;

import expression.exceptions.EvaluateException;

public class ModuleByZeroException extends EvaluateException {

    public ModuleByZeroException(String message) {
        super(message);
    }
    public ModuleByZeroException(String message, Exception cause) {
        super(message, cause);
    }
    public ModuleByZeroException() {
        super("Module by zero");
    }
}
