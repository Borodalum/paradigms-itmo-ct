package expression.exceptions;

import expression.AbstractExpression;
import expression.Add;

public class CheckedAdd extends Add {
    public CheckedAdd(AbstractExpression firstExp, AbstractExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected int getResult(int firstOperand, int secondOperand) {
        if (firstOperand > 0 && secondOperand > 0 && (firstOperand + secondOperand <= 0)
        || firstOperand < 0 && secondOperand < 0 && (firstOperand + secondOperand >= 0)) {
            throw new OverflowException("Overflow in sum");
        }
        return firstOperand + secondOperand;
    }
}