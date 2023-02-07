package expression;

public class Count implements AbstractExpression {
    private final AbstractExpression exp;

    public Count(AbstractExpression exp) {
        this.exp = exp;
    }

    public int evaluate(int x) {
        return Integer.bitCount(exp.evaluate(x));
    }

    public int evaluate(int x, int y, int z) {
        return Integer.bitCount(exp.evaluate(x, y, z));
    }

    protected AbstractExpression getInside() {
        return exp;
    }

    @Override
    public String toString() {
        return "count" + "(" + exp.toString() + ")";
    }

    @Override
    public boolean equals(Object exp) {
        return (exp instanceof AbstractExpression) && ((Count) exp).getInside().equals(exp);
    }

    @Override
    public int hashCode() {
        return ("count".hashCode() * 31 + (exp.hashCode())) * 31;
    }

}

