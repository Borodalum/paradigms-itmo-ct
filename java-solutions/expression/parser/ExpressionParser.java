package expression.parser;

import expression.*;
import expression.exceptions.CheckedNegate;

import java.util.Map;

public class ExpressionParser extends BaseParser implements TripleParser {
    private static final Map<String, Integer> PRIORITIES = Map.of(
            "+", 0, "-", 1,
            "*", 2, "/", 2
    );

    private int balance = 0;

    public ExpressionParser() {
    }

    public TripleExpression parse(String expression) {
        setSource(expression);

        this.balance = 0;
        AbstractExpression main = parseOperation(parseOperand(), -1, "");
        while (!eof()) {
            main = parseOperation(main, -1, "");
        }
        return main;
    }

    private AbstractExpression parseOperand() {
        skipWhitespaces();
        if (expect('x', 'y', 'z')) {
            String var = String.valueOf(currentSymbol());
            take();
            return new Variable(var);
        } else if (beetwin('0', '9')) {
            return new Const(parseInteger(false));
        } else if (expect('-')) {
            take();
            return parseNegated();
        } else if (expect('c')) {
            take();
            return parseCount();
        } else if (expect('(')) {
            take();
            balance++;
            return parseBrackets();
        }
        throw error("expect operand, take", true, true);
    }

    private AbstractExpression parseOperation(AbstractExpression exp, int lastPriority, String lastOperation) {
        skipWhitespaces();
        if (eof() || expect(')')) {
            int lastBalance = balance;
            if (expect(')')) {
                balance--;
            }
            take();
            if (eof() && balance != 0) {
                if (lastBalance == balance) {
                    throw error("missing parenthesis", true, false);
                } else {
                    throw error("extra parenthesis", true, false);
                }
            }
            return exp;
        }
        if (expect('+', '-', '*', '/')) {
            String curSymb = String.valueOf(currentSymbol());
            if (lastPriority == -1) {
                take();
                if (PRIORITIES.get(curSymb) <= 1) {
                    return convertOperation(curSymb, exp, parseOperation(parseOperand(), PRIORITIES.get(curSymb), curSymb));
                } else {
                    return parseOperation(convertOperation(curSymb, exp, parseOperand()), PRIORITIES.get(curSymb), curSymb);
                }
            }
            if (lastPriority < PRIORITIES.get(curSymb)) {
                take();
                if (curSymb.equals("/") || curSymb.equals("*")) {
                    return parseOperation(convertOperation(curSymb, exp, parseOperand()), PRIORITIES.get(curSymb), curSymb);
                }
                return convertOperation(curSymb, exp, parseOperation(parseOperand(), PRIORITIES.get(curSymb), curSymb));
            } else {
                if (lastPriority == PRIORITIES.get(curSymb) && lastPriority == 2) {
                    take();
                    if (lastOperation.equals("/") || lastOperation.equals("*")) {
                        return parseOperation(convertOperation(curSymb, exp, parseOperand()), PRIORITIES.get(curSymb), curSymb);
                    }
                }
                return exp;
            }
        }
        throw error("expect operation, take", true, true);
    }

    private AbstractExpression parseCount() {
        while (expect('o', 'u', 'n', 't')) {
            take();
        }
        if (!Character.isWhitespace(currentSymbol()) && currentSymbol() != '(') {
            throw error("expect whitespace or bracket after count, take", true, true);
        }
        return new Count(parseOperand());
    }
    private AbstractExpression parseNegated() {
        if (beetwin('0', '9'))
            return new Const(parseInteger(true));
        return convertUnary("-", parseOperand());
    }

    private AbstractExpression parseBrackets() {
        int lastBalance = this.balance;
        AbstractExpression bracket = parseOperation(parseOperand(), -1, "");
        while (!eof() && this.balance >= lastBalance) {
            bracket = parseOperation(bracket, -1, "");
        }
        return bracket;
    }

    protected AbstractExpression convertOperation(String operation, AbstractExpression fOperand, AbstractExpression sOperand) {
        switch (operation) {
            case "+" -> {
                return new Add(fOperand, sOperand);
            }
            case "-" -> {
                return new Subtract(fOperand, sOperand);
            }
            case "*" -> {
                return new Multiply(fOperand, sOperand);
            }
            case "/" -> {
                return new Divide(fOperand, sOperand);
            }
        }
        throw error("unsupported operation to convert: ", false, true);
    }

    protected AbstractExpression convertUnary(String sign, AbstractExpression operand) {
        switch (sign) {
            case "-" -> {
                return new CheckedNegate(operand);
            }
        }
        throw error("unsupported unary to convert: ", false, true);
    }
}
