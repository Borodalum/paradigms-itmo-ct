package expression;

public class Clear extends BinaryExpression {

    public Clear(AbstractExpression firstOperand, AbstractExpression secondOperand) {
        super(firstOperand, secondOperand, "clear");
    }

    @Override
    protected int getResult(int firstOperand, int secondOperand) {
        return firstOperand & ~(1 << secondOperand);
    }
}
