package expression.generic;

import expression.exceptions.*;
import expression.generic.expressions.AbstractExpression;
import expression.generic.parser.ExpressionParser;
import expression.generic.types.*;

public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2)
            throws Exception {
        return getTable(getType(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    private Type<?> getType(String mode) {
        switch (mode) {
            case "i" -> {
                return new IntegerType();
            }
            case "d" -> {
                return new DoubleType();
            }
            case "bi" -> {
                return new BigIntegerType();
            }
            case "s" -> {
                return new ShortType();
            }
            case "l" -> {
                return new LongType();
            }
            case "u" -> {
                return new UnchekedIntegerType();
            }
            default -> {
                throw new UnknownModeException();
            }
        }
    }

    private <T> Object[][][] getTable(Type<T> type, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        int rangeX = x2 - x1 + 1;
        int rangeY = y2 - y1 + 1;
        int rangeZ = z2 - z1 + 1;

        Object[][][] result = new Object[rangeX][rangeY][rangeZ];
        ExpressionParser<T> expParser = new ExpressionParser<>(type);
        AbstractExpression<T> exp = expParser.parse(expression);

        for (int i = 0; i < rangeX; i++) {
            for (int j = 0; j < rangeY; j++) {
                for (int k = 0; k < rangeZ; k++) {
                    try {
                        result[i][j][k] = exp.evaluate(type.convert(String.valueOf(x1 + i)),
                                type.convert(String.valueOf(y1 + j)), type.convert(String.valueOf(z1 + k)));
                    } catch (EvaluateException e) {
                        result[i][j][k] = null;
                    }
                }
            }
        }
        return result;
    }
}
