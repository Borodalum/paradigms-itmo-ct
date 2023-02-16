package search;

public class BinarySearchShift {
    // Pred: a -> отсортированный массив, если a.len == 0: shifted count = 0.
    // Post: k: shift a at k -> forall i in Iters forall j in Iters: i < j: a[i] > a[j]
    public static void main(String[] args) {
        // forall i in Iters forall j in Iters: j > i => a[i] >= a[j], но shifted
        int[] a = new int[args.length];

        // i = 0
        int i = 0;
        // P: i < a.len
        while (i < a.length) {
            // I: i' < a.len
            a[i] = Integer.parseInt(args[i]);
            i = i + 1;
            // i' = i' + 1
            // I: i' + 1 < a.len
            // I: i' < a.len - 1
        }
        // i > a.len && int x && forall i in Iters forall j in Iters: j > i => a[i] >= a[j], but shifted
        if (a.length != 0) {
            // pred: a.len > 0
            System.out.println(iterBinSearch(a));
        } else {
            System.out.println(0);
        }
        // Post: k: shift a at k -> forall i in Iters forall j in Iters: i < j: a[i] > a[j]
    }

    // Pred: a.len != 0, else k = 0 && return shift count k >= 0
    // Post: k: shift a at k -> forall i in Iters forall j in Iters: i < j: a[i] > a[j]
    private static int iterBinSearch(int[] a) {
        // left = -1
        int left = -1;
        // right = a.len
        int right = a.length;
        // I: left' < right' - 1
        while (left < right - 1) {
            // mid = left + (right - left) / 2
            int mid = left + (right - left) / 2;

            // P: a[mid] < [a.len - 1] && mid > 0 <- очевидно, из-за ограничений left и right
            if (a[mid] < a[a.length - 1]) {
                // left' = mid
                left = mid;
            } else {
                // right' = mid
                right = mid;
            }
            // мы двигаем границы так, чтобы найти минимум массива - его индекс и есть сдвиг
            // I: left'/2 + right'/2 < right' - 1 || right'/2 + left'/2 - 1 < left'
            // I: left'/2 < right'/2 - 1 || right'/2 - 1 < left'/2
            // I: left'/2 < right'/2 - 1
        }
        // right': shift a at right' -> forall i in Iters forall j in Iters: i < j: a[i] > a[j]
        return right;
    }
}
