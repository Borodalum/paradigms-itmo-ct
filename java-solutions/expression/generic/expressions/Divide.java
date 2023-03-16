package expression.generic.expressions;

import expression.generic.types.Type;

public class Divide<T> extends BinaryExpression<T> {
    public Divide(AbstractExpression<T> firstExp, AbstractExpression<T> secondExp, Type<T> type) {
        super(firstExp, secondExp, type);
    }

    @Override
    protected T getResult(T firstOperand, T secondOperand) {
        return type.div(firstOperand, secondOperand);
    }
}
