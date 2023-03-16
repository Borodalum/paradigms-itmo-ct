package expression.generic.expressions;

import expression.generic.types.Type;

public abstract class BinaryExpression<T> implements AbstractExpression<T> {

    private final AbstractExpression<T> fExp;
    private final AbstractExpression<T> sExp;
    protected final Type<T> type;

    public BinaryExpression(final AbstractExpression<T> firstExp, final AbstractExpression<T> secondExp, final Type<T> type) {
        this.fExp = firstExp;
        this.sExp = secondExp;
        this.type = type;
    }

    public T evaluate(T x, T y, T z) {
        return getResult(fExp.evaluate(x, y, z), sExp.evaluate(x, y, z));
    }
    protected abstract T getResult(T firstOperand, T secondOperand);
}
