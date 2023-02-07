package expression.exceptions;

public class Main {
    public static void main(String[] args) {
        ExpressionParser eP = new ExpressionParser();
        //System.out.println(eP.parse("1000000*x*x*x*x*x/(x-1)").evaluate(1, 0, 0));
        System.out.println(eP.parse("").evaluate(0, 0, 0));
    }
}
