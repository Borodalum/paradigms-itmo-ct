package expression.exceptions;

import expression.AbstractExpression;
import expression.Subtract;

public class CheckedSubtract extends Subtract {
    public CheckedSubtract(AbstractExpression firstExp, AbstractExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected int getResult(int firstOperand, int secondOperand) {
<<<<<<< HEAD
        if (firstOperand >= 0 && secondOperand < 0 && (firstOperand - secondOperand < 0)
                || firstOperand < 0 && secondOperand > 0 && (firstOperand - secondOperand > 0)) {
=======
        if (firstOperand >= 0 && secondOperand < 0 && (Integer.MAX_VALUE + secondOperand < firstOperand)
        || firstOperand < 0 && secondOperand >= 0 && (Integer.MIN_VALUE + secondOperand > firstOperand)) {
>>>>>>> aa07406a06f9437bf8d6f7cd34396b96d2340558
            throw new OverflowException("Overflow in subtract");
        }
        return firstOperand - secondOperand;
    }
}
