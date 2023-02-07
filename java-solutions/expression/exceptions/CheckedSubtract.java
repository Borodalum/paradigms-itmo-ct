package expression.exceptions;

import expression.AbstractExpression;
import expression.BinaryExpression;
import expression.Subtract;

public class CheckedSubtract extends Subtract {
    public CheckedSubtract(AbstractExpression firstExp, AbstractExpression secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected int getResult(int firstOperand, int secondOperand) {
        if (firstOperand >= 0 && secondOperand < 0 && (firstOperand - secondOperand < 0)
        || firstOperand < 0 && secondOperand > 0 && (firstOperand - secondOperand > 0)) {
            throw new OverflowException("Overflow while subtracting");
        }
        return firstOperand - secondOperand;
    }
}
