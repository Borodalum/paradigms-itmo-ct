package expression.generic.expressions;

public interface AbstractExpression<T> {
    T evaluate(T x, T y, T z);
    /*
        int evaluate(int x);
        int evaluate(int x, int y, int z);
        boolean equals(Object exp);
        String toString();
     */
}
