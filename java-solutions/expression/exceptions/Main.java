package expression.exceptions;

import expression.AbstractExpression;
import expression.TripleExpression;

public class Main {
    public static void main(String[] args) {
        ExpressionParser eP = new ExpressionParser();
        int spaceCount = 7;

        TripleExpression parsedExp = eP.parse("1000000*x*x*x*x*x/(x-1)");

        System.out.println("x" + " ".repeat(spaceCount - 1) + "f");
        for (int i = 0; i < 11; i++) {
            int spaces = spaceCount - Integer.toString(i).length();
            try {
                System.out.println(i + " ".repeat(spaces) + parsedExp.evaluate(i, 0, 0));
            } catch (EvaluateException e) {
                System.out.println(i + " ".repeat(spaces) + e.getMessage());
            }
        }
    }
}
