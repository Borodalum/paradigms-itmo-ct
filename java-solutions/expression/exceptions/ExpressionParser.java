package expression.exceptions;

import expression.*;

public final class ExpressionParser extends expression.parser.ExpressionParser {
    public ExpressionParser() {
    }

    @Override
    protected AbstractExpression convertOperation(String operation, AbstractExpression fOperand, AbstractExpression sOperand) {
        switch (operation) {
            case "+" -> {
                return new CheckedAdd(fOperand, sOperand);
            }
            case "-" -> {
                return new CheckedSubtract(fOperand, sOperand);
            }
            case "*" -> {
                return new CheckedMultiply(fOperand, sOperand);
            }
            case "/" -> {
                return new CheckedDivide(fOperand, sOperand);
            }
            case "set" -> {
                return new Set(fOperand, sOperand);
            }
            case "clear" -> {
                return new Clear(fOperand, sOperand);
            }
        }
        throw error("unsupported operation to convert: ", false, true);
    }

    @Override
    protected AbstractExpression convertUnary(String sign, AbstractExpression operand) {
        switch (sign) {
            case "-" -> {
                return new CheckedNegate(operand);
            }
        }
        throw error("unsupported unary to convert: ", false, true);
    }
}
