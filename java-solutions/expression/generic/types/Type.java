package expression.generic.types;

public interface Type<T> {
    T sum(T x, T y);
    T sub(T x, T y);
    T mul(T x, T y);
    T div(T x, T y);
    T negate(T x);
    T abs(T x);
    T square(T x);
    T mod (T x, T y);
    T convert(String string);
}
