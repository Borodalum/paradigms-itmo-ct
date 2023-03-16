package expression.generic.parser;

import expression.generic.expressions.*;
import expression.generic.types.Type;

import java.util.Map;

public class ExpressionParser<T> extends BaseParser<T> {
    private static final Map<String, Integer> PRIORITIES = Map.of(
            "+", 0, "-", 1,
            "*", 2, "/", 2,
            "mod", 2
            /*"set", 0, "clear", 0*/
    );

    private int balance = 0;

    public ExpressionParser(Type<T> type) {
        super(type);
    }

    public AbstractExpression<T> parse(String expression) {
        setSource(expression);
        this.balance = 0;
        AbstractExpression<T> main = parseOperation(parseOperand(), -1, "");
        while (!eof()) {
            main = parseOperation(main, -1, "");
        }
        return main;
    }

    private AbstractExpression<T> parseOperand() {
        skipWhitespaces();
        if (expect('x', 'y', 'z')) {
            String var = String.valueOf(currentSymbol());
            take();
            return new Variable<>(var);
        } else if (beetwin('0', '9')) {
            return new Const<>(parseInteger(false));
        } else if (expect('-')) {
            take();
            return parseNegated();
        } else if (expect('a')) {
            take();
            return parseAbs();
        } else if (expect('s')) {
            take();
            return parseSquare();
        /*} else if (expect('c')) {
            take();
            return parseCount();*/
        } else if (expect('(')) {
            take();
            balance++;
            return parseBrackets();
        }
        throw error("expect operand, take", true, true);
    }

    private AbstractExpression<T> parseOperation(AbstractExpression<T> exp, int lastPriority, String lastOperation) {
        if (expect('m') & !(Character.isWhitespace(getLastCh()) || getLastCh() == ')' || getLastCh() == '(')) {
            throw error("expect whitespace before operation mod", true ,true);
        }
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
        if (expect('+', '-', '*', '/', 's', 'c', 'm')) {
            String curSymb = String.valueOf(currentSymbol());
            /*if (expect('c')) {
                curSymb = "clear";
            }
            if (expect('s')) {
                curSymb = "set";
            }*/
            if (expect('m')) {
                curSymb = "mod";
            }
            if (lastPriority == -1) {
                /*if (curSymb.equals("set") || curSymb.equals("clear")) {
                    for (int i = 0; i < curSymb.length() - 1; i++) {
                        take();
                    }
                }*/
                if (curSymb.equals("mod")) {
                    for (int i = 0; i < curSymb.length() - 1; i++) {
                        take();
                    }
                }
                take();
                if (PRIORITIES.get(curSymb) <= 1) {
                    return convertOperation(curSymb, exp, parseOperation(parseOperand(), PRIORITIES.get(curSymb), curSymb));
                } else {
                    return parseOperation(convertOperation(curSymb, exp, parseOperand()), PRIORITIES.get(curSymb), curSymb);
                }
            }
            if (lastPriority < PRIORITIES.get(curSymb)) {
                take();
                if (curSymb.equals("/") || curSymb.equals("*") || curSymb.equals("mod")) {
                    return parseOperation(convertOperation(curSymb, exp, parseOperand()), PRIORITIES.get(curSymb), curSymb);
                }
                return convertOperation(curSymb, exp, parseOperation(parseOperand(), PRIORITIES.get(curSymb), curSymb));
            } else {
                if (lastPriority == PRIORITIES.get(curSymb) && lastPriority == 2) {
                    take();
                    if (lastOperation.equals("/") || lastOperation.equals("*") || lastOperation.equals("mod")) {
                        return parseOperation(convertOperation(curSymb, exp, parseOperand()), PRIORITIES.get(curSymb), curSymb);
                    }
                }
                return exp;
            }
        }
        throw error("expect operation, take", true, true);
    }

    /*private AbstractExpression<T> parseCount() {
        while (expect('o', 'u', 'n', 't')) {
            take();
        }
        if (!Character.isWhitespace(currentSymbol()) && currentSymbol() != '(') {
            throw error("expect whitespace or bracket after count, take", true, true);
        }
        return new Count(parseOperand());
    }*/
    private AbstractExpression<T> parseNegated() {
        if (beetwin('0', '9'))
            return new Const<>(parseInteger(true));
        return convertUnary("-", parseOperand());
    }

    private AbstractExpression<T> parseAbs() {
        if (Character.isWhitespace(currentSymbol()) || expect('+', '/', '*', '-')) {
            throw error("expect abs, take space", true, false);
        }
        while (expect('b', 's')) {
            take();
        }
        return convertUnary("abs", parseOperand());
    }
    private AbstractExpression<T> parseSquare() {
        if (Character.isWhitespace(currentSymbol()) || expect('+', '/', '*', '-')) {
            throw error("expect square, take space", true, false);
        }
        while (expect('q', 'u', 'a', 'r', 'e')) {
            take();
        }
        return convertUnary("square", parseOperand());
    }

    private AbstractExpression<T> parseBrackets() {
        int lastBalance = this.balance;
        AbstractExpression<T> bracket = parseOperation(parseOperand(), -1, "");
        while (!eof() && this.balance >= lastBalance) {
            bracket = parseOperation(bracket, -1, "");
        }
        return bracket;
    }

    protected AbstractExpression<T> convertOperation(String operation, AbstractExpression<T> fOperand, AbstractExpression<T> sOperand) {
        switch (operation) {
            case "+" -> {
                return new Add<>(fOperand, sOperand, type);
            }
            case "-" -> {
                return new Subtract<>(fOperand, sOperand, type);
            }
            case "*" -> {
                return new Multiply<>(fOperand, sOperand, type);
            }
            case "/" -> {
                return new Divide<>(fOperand, sOperand, type);
            }
            case "mod" -> {
                return new Mod<>(fOperand, sOperand, type);
            }
            /*case "set" -> {
                return new Set(fOperand, sOperand);
            }
            case "clear" -> {
                return new Clear(fOperand, sOperand);
            }*/
        }
        throw error("unsupported operation to convert:", false, true);
    }

    protected AbstractExpression<T> convertUnary(String sign, AbstractExpression<T> operand) {
        switch (sign) {
            case "-" -> {
                return new Negate<>(operand, type);
            }
            case "abs" -> {
                return new Abs<>(operand, type);
            }
            case "square" -> {
                return new Square<>(operand, type);
            }
        }
        throw error("unsupported unary to convert: ", false, true);
    }
}
