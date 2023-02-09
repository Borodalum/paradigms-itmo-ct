package expression;

public class Add extends BinaryExpression {
    public Add(AbstractExpression firstExp, AbstractExpression secondExp) {
        super(firstExp, secondExp, "+");
    }

    @Override
    protected int getResult(int firstOperand, int secondOperand) {
        return firstOperand + secondOperand;
    }
}
