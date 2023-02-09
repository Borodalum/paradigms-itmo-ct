package expression;

public class Set extends BinaryExpression {

    public Set(AbstractExpression firstOperand, AbstractExpression secondOperand) {
        super(firstOperand, secondOperand,"set");
    }

    @Override
    protected int getResult(int firstOperand, int secondOperand) {
        return firstOperand | (1 << secondOperand);
    }
}
