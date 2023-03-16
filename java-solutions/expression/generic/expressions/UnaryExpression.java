package expression.generic.expressions;

import expression.generic.types.Type;

public abstract class UnaryExpression<T> implements AbstractExpression<T> {
    private final AbstractExpression<T> exp;
    protected final Type<T> type;

    public UnaryExpression(final AbstractExpression<T> exp, final Type<T> type) {
        this.exp = exp;
        this.type = type;
    }

    public T evaluate(T x, T y, T z) {
        return getResult(exp.evaluate(x, y, z));
    }
    protected abstract T getResult(T operand);
}
