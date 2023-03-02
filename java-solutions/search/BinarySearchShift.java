package search;

public class BinarySearchShift {
    // Pred: предпологается, что нам дали отсортированный по убыванию массив, элементы которого
    // передвинули на индекс i' = (i + k) % a.len, где k - величина циклического сдвига.
    // Обозначим такой массив как sorted and shifted
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

            // P: a[mid] < [a.len - 1] && mid > 0 <- очевидно, из-за ограничений left и right
            if (a[mid] < a[a.length - 1]) {
                // left' = mid
                left = mid;
                // a[right'] > a[a.len - 1] > a[left']
            } else {
                // right = mid
                right = mid;
                // a[right'] > a[a.len - 1] > a[left']
            }
            // I: left/2 + right/2 != right - 1 || right/2 + left/2 - 1 != left && a[right'] > a[a.len - 1] > a[left']
            // I: left/2 != right/2 - 1 || right/2 - 1 != left/2 && a[right'] > a[a.len - 1] > a[left']
            // I: left/2 != right/2 - 1 && a[right'] > a[a.len - 1] > a[left']
            // left' = left / 2, right' = right/2 && a[right'] > a[a.len - 1] > a[left']
            // I: left' != right' - 1 && a[right'] > a[a.len - 1] > a[left']
        }
        // a[right'] > a[a.len - 1] > a[left'] && left + 1 == right
        // наблюдается резкий переход между числами, тогда в right - 'экстремум' =>
        // right': сдвинув sorted and shifted массив a на right', получим sorted and not shifted.
        return right;
    }
}
