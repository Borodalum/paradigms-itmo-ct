package expression;

import java.util.InputMismatchException;

public class Variable implements AbstractExpression {
    private final String var;

    public Variable(String var) {
        this.var = var;
    }

    public int evaluate(int x) {
        return x;
    }

    public int evaluate(int x, int y, int z) {
        return switch (var) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> throw new InputMismatchException("I can work with variable names 'x', 'y', 'z'. Sorry %(");
        };
    }

    @Override
    public String toString() {
        return var;
    }

    @Override
    public boolean equals(Object otherExp) {
        return (otherExp instanceof Variable) && ((Variable) otherExp).var.equals(var);
    }

    @Override
    public int hashCode() {
        return var.hashCode();
    }
}
