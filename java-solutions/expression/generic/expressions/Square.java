package expression.generic.expressions;

import expression.generic.types.Type;

public class Square<T> extends UnaryExpression<T> {
    public Square(AbstractExpression<T> exp, Type<T> type) {
        super(exp, type);
    }

    @Override
    protected T getResult(T firstOperand) {
        return type.square(firstOperand);
    }
}
