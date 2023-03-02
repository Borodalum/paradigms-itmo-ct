    package search;

public class BinarySearchShift {
    // Pred: a of int: for i: 0...k - 1: a[i] > a[i + 1] && for i: k + 1...n - 1: a[i] > a[i + 1]
    // && a[k] < a[k + 1] && a[0] < a[a.len]
    public static void main(String[] args) {
        // sorted and shifted
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
        // i > a.len && int x && sorted and shifted
        if (a.length != 0) {
            // pred: a.len > 0
            System.out.println(iterBinSearch(a));
        } else {
            System.out.println(0);
        }
        // Post: k: сдвинув sorted and shifted массив a на k, получим sorted and not shifted.
    }

    // Pred: a.len != 0, else k = 0 && return shift count k >= 0
    // && a of int: for i: 0...k - 1: a[i] > a[i + 1] && for i: k + 1...n - 1: a[i] > a[i + 1]
    // && a[k] < a[k + 1] && a[0] < a[a.len]
    // Post: k: сдвинув sorted and shifted массив a на k, получим sorted and not shifted.
    private static int iterBinSearch(int[] a) {
        // left = -1
        int left = -1;
        // right = a.len
        int right = a.length;
        // I: left' < right' - 1 && a[right] > a[a.len - 1] > a[left]
        while (left < right - 1) {
            // mid = (right + left) / 2
            int mid = left + (right - left) / 2;
            // left < mid < right
            // P: a[mid] < a[right] && left < m < right
            if (a[mid] < a[a.length - 1]) {
                // left' = mid
                left = mid;
                // a[right'] > a[a.len - 1] > a[left'] && Pred && left < m < right
            } else {
                // right = mid
                right = mid;
                // a[right'] > a[a.len - 1] > a[left'] && Pred && left < m < right
            }
            // left' <= left && right' <= right && right' - left' < right - left
            // && left < m < right
        }
        // a[right'] > a[a.len - 1] > a[left'] && left + 1 == right
        // наблюдается изменение знака с > на < между числами, тогда в right - 'экстремум' =>
        // right': сдвинув sorted and shifted массив a на right', получим sorted and not shifted.
        return right;
    }
}
