package expression.parser;

import expression.exceptions.OverflowException;
import expression.exceptions.ParseException;

public abstract class BaseParser {
    private static final char END = 0;
    private String source;
    private char ch;
    private char lastCh;
    private int pos;

    public BaseParser() {
    }

    protected void setSource(String source) {
        this.source = source;
        pos = 0;
        take();
    }

    protected void take() {
        if (pos < source.length()) {
            ch = source.charAt(pos);
            pos++;
        } else {
            ch = END;
        }
    }
    protected boolean take(char... expectedChars) {
        take();
        return expect(expectedChars);
    }

    protected boolean expect(char... expectedChars) {
        for (char ch : expectedChars) {
            if (this.ch == ch) {
                return true;
            }
        }
        return false;
    }

    protected char currentSymbol() {
        return ch;
    }

    protected boolean beetwin(char lowerBound, char upperBound) {
        return lowerBound <= ch && ch <= upperBound;
    }

    protected void skipWhitespaces() {
        lastCh = source.charAt(pos - 1);
        while (Character.isWhitespace(ch) || ch == '\u0085' || ch == '\u2028' || ch == '\u2029') {
            take();
        }
    }

    protected int parseInteger(boolean isNegative) {
        try {
            StringBuilder sb = new StringBuilder();
            if (isNegative) {
                sb.append('-');
            }
            while ('0' <= ch && ch <= '9') {
                sb.append(ch);
                take();
            }
            return Integer.parseInt(sb.toString());
        } catch (NumberFormatException e) {
            throw new OverflowException("Overflow in constant", e);
        }
    }

    protected ParseException error(String message, boolean showPos, boolean showCharacter) {
        StringBuilder sb = new StringBuilder();
        if (showPos) {
            sb.append("At pos ").append(pos).append(": ");
        }
        sb.append(message);
        if (showCharacter) {
            sb.append(" '").append(ch).append("'");
        }
        sb.append('.');
        return new ParseException(sb.toString());
    }

    protected boolean eof() {
        return ch == END;
    }
}
