package expression.generic.types;

import expression.exceptions.*;

public class ShortType implements Type<Short> {

    @Override
    public Short sum(Short x, Short y) {
        return (short) (x + y);
    }

    @Override
    public Short sub(Short x, Short y) {
        return (short) (x - y);
    }

    @Override
    public Short mul(Short x, Short y) {
        return (short) (x * y);
    }

    @Override
    public Short div(Short x, Short y) {
        if ((int)y == 0) {
            throw new DivisionByZeroException("Dividing by zero");
        }
        return (short)(x / y);
    }

    @Override
    public Short negate(Short x) {
        return (short)-x;
    }

    @Override
    public Short abs(Short x) {
        return (short)Math.abs(x);
    }

    @Override
    public Short mod(Short x, Short y) {
        if (y == (short)0) {
            throw new EvaluateException("dasdas");
        }
        return (short)(x % y);
    }

    @Override
    public Short square(Short x) {
        return (short)(x * x);
    }

    @Override
    public Short convert(String string) {
        try {
            return (short)Integer.parseInt(string);
        } catch (NumberFormatException E) {
            throw new IllegalConstantExcpetion("Constant is illegal");
        }
    }
}
