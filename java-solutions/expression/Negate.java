package expression;

public class Negate implements AbstractExpression {
    protected final AbstractExpression exp;

    public Negate(AbstractExpression exp) {
        this.exp = exp;
    }

    public int evaluate(int x) {
        return -(exp.evaluate(x));
    }

    public int evaluate(int x, int y, int z) {
        return -(exp.evaluate(x, y, z));
    }

    protected AbstractExpression getInside() {
        return exp;
    }

    @Override
    public String toString() {
        return "-" + "(" + exp.toString() + ")";
    }

    @Override
    public boolean equals(Object exp) {
        return (exp instanceof AbstractExpression) && ((Negate) exp).getInside().equals(exp);
    }

    @Override
    public int hashCode() {
        return ("-".hashCode() * 31 + (exp.hashCode())) * 31;
    }

}
