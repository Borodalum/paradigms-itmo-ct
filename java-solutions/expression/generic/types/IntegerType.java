package expression.generic.types;

import expression.exceptions.*;

public class IntegerType implements Type<Integer> {

    @Override
    public Integer sum(Integer x, Integer y) {
        return checkAdd(x, y);
    }

    private Integer checkAdd(Integer x, Integer y) {
        if (x > 0 && (Integer.MAX_VALUE - x < y)
                || x < 0 && (Integer.MIN_VALUE - x > y)) {
            throw new OverflowException("Overflow in sum");
        }
        return x + y;
    }

    @Override
    public Integer sub(Integer x, Integer y) {
        return checkSub(x, y);
    }

    private Integer checkSub(Integer x, Integer y) {
        if (x >= 0 && y < 0 && (Integer.MAX_VALUE + y < x)
                || x < 0 && y >= 0 && (Integer.MIN_VALUE + y > x)) {
            throw new OverflowException("Overflow in subtract");
        }
        return x - y;
    }

    @Override
    public Integer mul(Integer x, Integer y) {
        return checkedMul(x, y);
    }

    private Integer checkedMul(Integer x, Integer y) {
        if ((x > 0 && y < 0 && Integer.MIN_VALUE / x > y)
                || (x < 0 && y > 0 && Integer.MIN_VALUE / y > x)
                || (x > 0 && y > 0 && Integer.MAX_VALUE / x < y)
                || (x < 0 && y < 0 && Integer.MAX_VALUE / x > y)) {
            throw new OverflowException("Overflow in multiply");
        }
        return x * y;
    }

    @Override
    public Integer div(Integer x, Integer y) {
        return checkedDiv(x, y);
    }

    private Integer checkedDiv(Integer x, Integer y) {
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException("dsad");
        }
        if (y == 0) {
            throw new DivisionByZeroException("dsada");
        }
        return x / y;
    }

    @Override
    public Integer negate(Integer x) {
        return checkedNegate(x);
    }

    private Integer checkedNegate(Integer x) {
        checkMinValue(x);
        return -(x);
    }

    @Override
    public Integer abs(Integer x) {
        checkMinValue(x);
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

    private void checkMinValue(Integer x ) {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException("dsadas");
        }
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
