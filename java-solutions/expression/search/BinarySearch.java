package search;

public class BinarySearch {
    // Контракт
    // Pred: Строка, из которой возможно выделить целые числа
    // Post: int i = min(Iters): a[i] <= x, где x, a[i] - целые числа, выделенные из входной строки
    // или 0, при a.len == 0 или если такой индекс не найдется.
    public static void main(String[] args) {
        // int x, forall i in Iters forall j in Iters: j > i => a[i] >= a[j]
        int x = Integer.parseInt(args[0]);
        int[] a = new int[args.length - 1];

        // i = 0
        int i = 0;
        // sum = 0
        int sum = 0;
        // P: i < a.len
        while (i < a.length) {
            // I: i' < a.len
            a[i] = Integer.parseInt(args[i + 1]);
            // sum' = sum' + a[i]
            sum += a[i];
            i = i + 1;
            // i' = i' + 1
            // I: i' + 1 < a.len
            // I: i' < a.len - 1
        }
        // P: i > a.len && int x && forall i in Iters forall j in Iters: j > i => a[i] >= a[j]
        if (sum % 2 == 1) {
            // sum' % 2 == 1 && P
            System.out.println(iterBinSearch(a, x));
        } else {
            // sum' % 2 == 0 && P
            System.out.println(recBinSearch(a, x, -1, a.length));
        }

    }

    // Pred: int x && forall i in Iters forall j in Iters: j > i => a[i] >= a[j]
    // Post: int i: a[i] <= x && i = min(Iters)
    private static int iterBinSearch(int[] a, int x) {
        // left = -1
        int left = -1;
        // right = a.len
        int right = a.length;
        // I: left' != right' - 1
        while (left != right - 1) {
            // mid = (left' + right') / 2
            int mid = (left + right)/2;
            // P: mid < a.len && mid > 0 <- очевидно, из-за ограничений left и right
            if (a[mid] <= x) {
                // P && a[mid] <= x
                right = mid;
                // right' = mid
            } else {
                // P && a[mid] > x
                left = mid;
                // left' = mid
            }
            // I: left'/2 + right'/2 != right' - 1 || right'/2 + left'/2 - 1 != left'
            // I: left'/2 != right'/2 - 1 || right'/2 - 1 != left'/2
            // I: left'/2 != right'/2 - 1
        }
        // right': a[right] <= x && right' = min(Iters)
        return right;
    }

    // Pred: int x && forall i in Iters forall j in Iters: j > i => a[i] >= a[j] && int left < a.len && right > 0
    // Post: int i: a[i] <= x && i = min(Iters)
    private static int recBinSearch(int[] a, int x, int left, int right) {
        // P = Pred
        if (left >= right - 1) {
            // P && left > right - 1
            // right: a[right] <= x && right = min(Iters)
            return right;
        }
        // P && left < right - 1
        // mid = (left + right) / 2
        int mid = (left + right)/2;
        // P: mid < a.len && mid > 0 <- очевидно, из-за ограничений left и right
        if (a[mid] <= x) {
            // P && a[mid] <= x
            // P && (right' = mid) -> (right' > 0) && a' = a && x' = x && (left < a.len) -> (left' = left)
            return recBinSearch(a, x, left, mid);
        } else {
            // P && a[mid] > x
            // P && (left' = mid) -> (left' < a.len) && (right > 0) -> (right' = right) && a' = a && x' = x
            return recBinSearch(a, x, mid, right);
        }
    }
}
