package expression.generic.expressions;

import expression.generic.types.Type;

public class Mod<T> extends BinaryExpression<T> {
    public Mod(AbstractExpression<T> firstExp, AbstractExpression<T> secondExp, Type<T> type) {
        super(firstExp, secondExp, type);
    }

    @Override
    protected T getResult(T firstOperand, T secondOperand) {
        return type.mod(firstOperand, secondOperand);
    }
}
