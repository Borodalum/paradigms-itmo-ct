package expression.exceptions;

import expression.AbstractExpression;
import expression.Multiply;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(AbstractExpression firstExp, AbstractExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected int getResult(int firstOperand, int secondOperand) {
<<<<<<< HEAD
        int res = firstOperand * secondOperand;
        if ((secondOperand != 0 && (res / secondOperand != firstOperand)) ||
                firstOperand == Integer.MIN_VALUE && secondOperand == -1) {
=======
        if ((firstOperand > 0 && secondOperand < 0 && Integer.MIN_VALUE / firstOperand > secondOperand)
                || (firstOperand < 0 && secondOperand > 0 && Integer.MIN_VALUE / secondOperand > firstOperand)
                || (firstOperand > 0 && secondOperand > 0 && Integer.MAX_VALUE / firstOperand < secondOperand)
                || (firstOperand < 0 && secondOperand < 0 && Integer.MAX_VALUE / firstOperand > secondOperand)) {
>>>>>>> aa07406a06f9437bf8d6f7cd34396b96d2340558
            throw new OverflowException("Overflow in multiply");
        }
        return firstOperand * secondOperand;
    }
}