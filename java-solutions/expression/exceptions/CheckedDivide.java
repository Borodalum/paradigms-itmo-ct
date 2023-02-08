package expression.exceptions;

import expression.AbstractExpression;
import expression.Divide;

public class CheckedDivide extends Divide {
    public CheckedDivide(AbstractExpression firstExp, AbstractExpression secondExp) {
        super(firstExp, secondExp);
    }
    @Override
    protected int getResult(int firstOperand, int secondOperand) {
        if (firstOperand == Integer.MIN_VALUE && secondOperand == -1) {
            throw new OverflowException("Overflow in division");
        }
        if (secondOperand == 0) {
            throw new DivisionByZeroException("Division by zero");
        }
        return firstOperand / secondOperand;
    }
}
