package expression.generic.types;

import expression.exceptions.*;

public class UnchekedIntegerType implements Type<Integer> {

    @Override
    public Integer sum(Integer x, Integer y) {
        return x + y;
    }

    @Override
    public Integer sub(Integer x, Integer y) {
        return x - y;
    }

    @Override
    public Integer mul(Integer x, Integer y) {
        return x * y;
    }

    @Override
    public Integer div(Integer x, Integer y) {
        return checkedDiv(x, y);
    }

    private Integer checkedDiv(Integer x, Integer y) {
        if (y == 0) {
            throw new DivisionByZeroException("Division by zero");
        }
        return x / y;
    }

    @Override
    public Integer negate(Integer x) {
        return -x;
    }

    public Integer abs(Integer x) {
        return Math.abs(x);
    }

    @Override
    public Integer mod(Integer x, Integer y) {
        if (y == 0) {
            throw new ModuleByZeroException();
        }
        return x % y;
    }

    @Override
    public Integer square(Integer x) {
        return mul(x, x);
    }

    @Override
    public Integer convert(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            throw new IllegalConstantExcpetion("Constant is illegal");
        }
    }
}

