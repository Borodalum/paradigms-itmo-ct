package expression.generic.expressions;

import expression.generic.types.Type;

public class Abs<T> extends UnaryExpression<T>{
    public Abs(AbstractExpression<T> exp, Type<T> type) {
        super(exp, type);
    }

    @Override
    protected T getResult(T operand) {
        return type.abs(operand);
    }
}
