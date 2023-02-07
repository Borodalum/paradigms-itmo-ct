package expression.exceptions;

import expression.AbstractExpression;
import expression.Negate;

public class CheckedNegate extends Negate {

    public CheckedNegate(AbstractExpression exp) {
        super(exp);
    }

    @Override
    public int evaluate(int x) {
        int res = exp.evaluate(x);
        checkOverflow(res);

        return -(res);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int res = exp.evaluate(x, y, z);
        checkOverflow(res);

        return -(res);
    }

    private void checkOverflow(int value) {
        if (value == Integer.MIN_VALUE) {
            throw new OverflowException("Overflow while negating");
        }
    }

}
