package expression;

public class Subtract extends BinaryExpression {
    public Subtract(AbstractExpression firstExp, AbstractExpression secondExp) {
        super(firstExp, secondExp, "-");
    }

    @Override
    protected int getResult(int firstOperand, int secondOperand) {
        return firstOperand - secondOperand;
    }
}
