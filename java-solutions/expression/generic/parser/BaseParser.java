package expression.generic.parser;

import expression.exceptions.ParseException;
import expression.generic.types.Type;

public abstract class BaseParser<T> {
    private static final char END = 0;
    private String source;
    protected Type<T> type;
    private char ch;
    private char lastCh;
    private int pos;

    public BaseParser(Type<T> type) {
        this.type = type;
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
        //System.out.println(currentSymbol());
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
    protected char getLastCh() {
        return lastCh;
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

    protected T parseInteger(boolean isNegative) {
        StringBuilder sb = new StringBuilder();
        if (isNegative) {
            sb.append('-');
        }
        while ('0' <= ch && ch <= '9') {
            sb.append(ch);
            take();
        }
        return type.convert(sb.toString());
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
