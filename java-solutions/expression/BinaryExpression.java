package expression;

public abstract class BinaryExpression implements AbstractExpression {
    // :NOTE: паблик?
    private final AbstractExpression[] expressionList;
    private final String sign;

    public BinaryExpression(final AbstractExpression firstExp, final AbstractExpression secondExp, String sign) {
        this.expressionList = new AbstractExpression[]{firstExp, secondExp};
        this.sign = sign;
    }

    public int evaluate(int x) {
        return getResult(expressionList[0].evaluate(x), expressionList[1].evaluate(x));
    }

    public int evaluate(int x, int y, int z) {
        return getResult(expressionList[0].evaluate(x, y, z), expressionList[1].evaluate(x, y, z));
    }

    protected abstract int getResult(int firstOperand, int secondOperand);

    public String getSign() {
        return this.sign;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(expressionList[0].toString());
        for (int i = 1; i < expressionList.length; i++) {
            sb.append(" ").append(sign).append(" ").append(expressionList[i].toString());
        }
        sb.append(")");
        return sb.toString();
    }

    protected AbstractExpression getFirstOperand() {
        return expressionList[0];
    }

    protected AbstractExpression getSecondOperand() {
        return expressionList[1];
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BinaryExpression))
            return false;
        return this.sign.equals(((BinaryExpression) obj).getSign())
                && ((BinaryExpression) obj).getFirstOperand().equals(this.expressionList[0])
                && ((BinaryExpression) obj).getSecondOperand().equals(this.expressionList[1]);
    }

    @Override
    public int hashCode() {
        return (((sign.hashCode()) * 17 + expressionList[0].hashCode()) * 17
                + expressionList[1].hashCode()) * 17;
    }
}
