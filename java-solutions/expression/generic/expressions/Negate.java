package expression.generic.expressions;

import expression.generic.types.Type;

public class Negate<T> extends UnaryExpression<T> {
    public Negate(AbstractExpression<T> exp, Type<T> type) {
        super(exp, type);
    }

    @Override
    protected T getResult(T operand) {
        return type.negate(operand);
    }
}


