package expression;

public class Multiply extends BinaryExpression {
    public Multiply(AbstractExpression firstExp, AbstractExpression secondExp) {
        super(firstExp, secondExp, "*");
    }

    @Override
    protected int getResult(int firstOperand, int secondOperand) {
        return firstOperand * secondOperand;
    }
}
