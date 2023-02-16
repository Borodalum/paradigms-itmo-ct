package expression.exceptions;

import expression.AbstractExpression;
import expression.Add;

public class CheckedAdd extends Add {
    public CheckedAdd(AbstractExpression firstExp, AbstractExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected int getResult(int firstOperand, int secondOperand) {
        if (firstOperand > 0 && (Integer.MAX_VALUE - firstOperand < secondOperand)
                || firstOperand < 0 && (Integer.MIN_VALUE - firstOperand > secondOperand)) {
            throw new OverflowException("Overflow in sum");
        }
        return firstOperand + secondOperand;
    }
}