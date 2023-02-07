package expression;

public class Const implements AbstractExpression {
    private final int value;

    public Const(int value) {
        this.value = value;
    }

    public int evaluate(int x, int y, int z) {
        return value;
    }

    public int evaluate(int x) {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object otherExp) {
        // :NOTE: сравнение через toString???
        return (otherExp instanceof Const) && ((Const) otherExp).getValue() == this.getValue();
    }

    @Override
    public int hashCode() {
        return value;
    }
}
