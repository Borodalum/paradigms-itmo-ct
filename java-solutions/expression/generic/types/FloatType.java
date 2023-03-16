package expression.generic.types;

import expression.exceptions.IllegalConstantExcpetion;

public class FloatType implements Type<Float> {

    @Override
    public Float sum(Float x, Float y) {
        return x + y;
    }

    @Override
    public Float sub(Float x, Float y) {
        return x - y;
    }

    @Override
    public Float mul(Float x, Float y) {
        return x * y;
    }

    @Override
    public Float div(Float x, Float y) {
        return x / y;
    }

    @Override
    public Float negate(Float x) {
        return -x;
    }

    @Override
    public Float abs(Float x) {
        return Math.abs(x);
    }

    @Override
    public Float mod(Float x, Float y) {
        return x % y;
    }

    @Override
    public Float square(Float x) {
        return x * x;
    }

    @Override
    public Float convert(String string) {
        try {
            return Float.parseFloat(string);
        } catch (NumberFormatException e) {
            throw new IllegalConstantExcpetion("Constant is illegal");
        }
    }
}
