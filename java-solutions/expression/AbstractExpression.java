package expression;

public interface AbstractExpression extends Expression, TripleExpression {
    int evaluate(int x);

    int evaluate(int x, int y, int z);

    boolean equals(Object exp);

    String toString();
}
