package expression.exceptions;

import expression.AbstractExpression;
import expression.Add;

public class CheckedAdd extends Add {
    public CheckedAdd(AbstractExpression firstExp, AbstractExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected int getResult(int firstOperand, int secondOperand) {
<<<<<<< HEAD
        if (firstOperand > 0 && secondOperand > 0 && (firstOperand + secondOperand <= 0)
                || firstOperand < 0 && secondOperand < 0 && (firstOperand + secondOperand >= 0)) {
=======
        if (firstOperand > 0 && (Integer.MAX_VALUE - firstOperand < secondOperand)
        || firstOperand < 0 && (Integer.MIN_VALUE - firstOperand > secondOperand)) {
>>>>>>> aa07406a06f9437bf8d6f7cd34396b96d2340558
            throw new OverflowException("Overflow in sum");
        }
        return firstOperand + secondOperand;
    }
}