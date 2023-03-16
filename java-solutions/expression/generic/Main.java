package expression.generic;

public class Main {
    public static void main(String[] args) {
        GenericTabulator tabulator = new GenericTabulator();
        try {
            Object[][][] res = tabulator.
                    tabulate(args[0].substring(1), args[1], -2, 2, -2, 2, -2, 2);
            for (int i = 0; i <= 4; i++) {
                for (int j = 0; j <= 4; j++) {
                    for (int k = 0; k <= 4; k++) {
                        System.out.printf("Result for x=%d, y=%d, z=%d is ", i - 2, j - 2, k - 2);
                        System.out.print(res[i][j][k]);
                        System.out.println();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
