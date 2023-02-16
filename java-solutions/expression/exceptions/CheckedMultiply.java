package expression.exceptions;

import expression.AbstractExpression;
import expression.Multiply;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(AbstractExpression firstExp, AbstractExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected int getResult(int firstOperand, int secondOperand) {
        if ((firstOperand > 0 && secondOperand < 0 && Integer.MIN_VALUE / firstOperand > secondOperand)
                || (firstOperand < 0 && secondOperand > 0 && Integer.MIN_VALUE / secondOperand > firstOperand)
                || (firstOperand > 0 && secondOperand > 0 && Integer.MAX_VALUE / firstOperand < secondOperand)
                || (firstOperand < 0 && secondOperand < 0 && Integer.MAX_VALUE / firstOperand > secondOperand)) {
            throw new OverflowException("Overflow in multiply");
        }
        return firstOperand * secondOperand;
    }
}