package expression.generic.expressions;

import expression.generic.types.Type;

public class Add<T> extends BinaryExpression<T> {
    public Add(AbstractExpression<T> firstExp, AbstractExpression<T> secondExp, Type<T> type) {
        super(firstExp, secondExp, type);
    }

    @Override
    protected T getResult(T firstOperand, T secondOperand) {
        return type.sum(firstOperand, secondOperand);
    }
}
