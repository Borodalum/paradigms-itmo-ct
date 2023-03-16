package expression.generic.types;

import expression.exceptions.*;

public class LongType implements Type<Long> {

    @Override
    public Long sum(Long x, Long y) {
        return x + y;
        //return checkAdd(x, y);
    }

    private Long checkAdd(Long x, Long y) {
        if (x > 0 && (Long.MAX_VALUE - x < y)
                || x < 0 && (Long.MIN_VALUE - x > y)) {
            throw new OverflowException("Overflow in sum");
        }
        return x + y;
    }

    @Override
    public Long sub(Long x, Long y) {
        return x - y;
        //return checkSub(x, y);
    }

    private Long checkSub(Long x, Long y) {
        if (x >= 0 && y < 0 && (Long.MAX_VALUE + y < x)
                || x < 0 && y >= 0 && (Long.MIN_VALUE + y > x)) {
            throw new OverflowException("Overflow in subtract");
        }
        return x - y;
    }

    @Override
    public Long mul(Long x, Long y) {
        return x * y;
        //return checkedMul(x, y);
    }

    private Long checkedMul(Long x, Long y) {
        if ((x > 0 && y < 0 && Long.MIN_VALUE / x > y)
                || (x < 0 && y > 0 && Long.MIN_VALUE / y > x)
                || (x > 0 && y > 0 && Long.MAX_VALUE / x < y)
                || (x < 0 && y < 0 && Long.MAX_VALUE / x > y)) {
            throw new OverflowException("Overflow in multiply");
        }
        return x * y;
    }

    @Override
    public Long div(Long x, Long y) {
        return checkedDiv(x, y);
    }

    private Long checkedDiv(Long x, Long y) {
        /*if (x == Long.MIN_VALUE && y == -1) {
            throw new OverflowException("Overflow in division");
        }*/
        if (y == 0) {
            throw new DivisionByZeroException("Division by zero");
        }
        return x / y;
    }

    @Override
    public Long negate(Long x) {
        return -x;
        //return checkedNegate(x);
    }

    private Long checkedNegate(Long x) {
        if (x == Long.MIN_VALUE) {
            throw new OverflowException("Overflow in negate");
        }
        return -(x);
    }

    @Override
    public Long abs(Long x) {
        return Math.abs(x);
    }

    @Override
    public Long mod(Long x, Long y) {
        if (y == 0) {
            throw new ModuleByZeroException();
        }
        return x % y;
    }

    @Override
    public Long square(Long x) {
        return mul(x, x);
    }

    @Override
    public Long convert(String string) {
        try {
            return Long.parseLong(string);
        } catch (NumberFormatException e) {
            throw new IllegalConstantExcpetion("Constant is illegal");
        }
    }
}

