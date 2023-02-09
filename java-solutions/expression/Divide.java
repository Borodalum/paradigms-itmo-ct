package expression;

public class Divide extends BinaryExpression {
    public Divide(AbstractExpression firstExp, AbstractExpression secondExp) {
        super(firstExp, secondExp, "/");
    }
    @Override
    protected int getResult(int firstOperand, int secondOperand) {
        return firstOperand / secondOperand;
    }
}
