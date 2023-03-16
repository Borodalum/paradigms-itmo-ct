package expression.generic.types;

import expression.exceptions.IllegalConstantExcpetion;

public class DoubleType implements Type<Double> {

    @Override
    public Double sum(Double x, Double y) {
        return x + y;
    }

    @Override
    public Double sub(Double x, Double y) {
        return x - y;
    }

    @Override
    public Double mul(Double x, Double y) {
        return x * y;
    }

    @Override
    public Double div(Double x, Double y) {
        return x / y;
    }

    @Override
    public Double negate(Double x) {
        return -x;
    }

    @Override
    public Double abs(Double x) {
        return Math.abs(x);
    }

    @Override
    public Double mod(Double x, Double y) {
        return x % y;
    }

    @Override
    public Double square(Double x) {
        return x * x;
    }

    @Override
    public Double convert(String string) {
        try {
            return Double.parseDouble(string);
        } catch (NumberFormatException e) {
            throw new IllegalConstantExcpetion("Constant is illegal");
        }
    }
}
