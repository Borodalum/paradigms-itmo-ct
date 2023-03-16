package expression.generic.types;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.IllegalConstantExcpetion;
import expression.exceptions.ModuleByZeroException;

import java.math.BigInteger;

public class BigIntegerType implements Type<BigInteger> {

    @Override
    public BigInteger sum(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    @Override
    public BigInteger sub(BigInteger x, BigInteger y) {
        return x.subtract(y);
    }

    @Override
    public BigInteger mul(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }

    @Override
    public BigInteger div(BigInteger x, BigInteger y) {
        if (y.equals(BigInteger.ZERO)) {
            throw new DivisionByZeroException("Dividing by zero");
        }
        return x.divide(y);
    }

    @Override
    public BigInteger negate(BigInteger x) {
        return x.negate();
    }

    @Override
    public BigInteger abs(BigInteger x) {
        return x.abs();
    }

    @Override
    public BigInteger mod(BigInteger x, BigInteger y) {
        if (y.compareTo(BigInteger.ZERO) <= 0) {
            throw new ModuleByZeroException();
        }
        return x.mod(y);
    }

    @Override
    public BigInteger square(BigInteger x) {
        return mul(x, x);
    }

    @Override
    public BigInteger convert(String string) {
        try {
            return new BigInteger(string);
        } catch (NumberFormatException E) {
            throw new IllegalConstantExcpetion("Constant is illegal");
        }
    }
}
