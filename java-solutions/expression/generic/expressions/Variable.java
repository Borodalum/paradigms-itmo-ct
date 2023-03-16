package expression.generic.expressions;

import java.util.InputMismatchException;

public class Variable<T> implements AbstractExpression<T> {
    private final String var;

    public Variable(String var) {
        this.var = var;
    }

    public T evaluate(T x, T y, T z) {
        return switch (var) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> throw new InputMismatchException("I can work with variable names 'x', 'y', 'z'. Sorry %(");
        };
    }

}
