package expression.exceptions;

import expression.AbstractExpression;
import expression.Multiply;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(AbstractExpression firstExp, AbstractExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected int getResult(int firstOperand, int secondOperand) {
        int res = firstOperand * secondOperand;
        if ((secondOperand != 0 && (res / secondOperand != firstOperand)) ||
            firstOperand == Integer.MIN_VALUE && secondOperand == -1) {
            throw new OverflowException("Overflow in multiply");
        }
        return firstOperand * secondOperand;
    }
}