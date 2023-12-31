package queue;

import java.util.Arrays;
import java.util.Objects;

public class ArrayQueueModule{
    // Model: a[1]...a[n]
    // Invariant: for i = 1...n: a[i] != null && n >= 0

    // Let immutable(n): for i in 1...n: a[i] == a'[i]
    private static int size = 0;
    private static int head = 0;

    private static Object[] elements = new Object[2];

    // Pred: element != null;
    // Post: n' = n + 1 && elements[1] = element && immutable(n)
    public static void enqueue(final Object element) {
        assert element != null;
        Objects.requireNonNull(element);
        ensureCapacity();
        elements[(head + size) % elements.length] = element;
        size++;
        //System.out.println(head % elements.length + " and tail " + (head + size) % elements.length);
    }

    // Pred: size > 0
    // Post: R = element && elements[1] = element && element != null && immutable(n)
    public static Object element() {
        assert size > 0;
        ensureCapacity();
        return elements[head % elements.length];
    }

    // Pred: size > 0
    // Post: n' = n - 1 && R = element && elements[1] = element && element != null && immutable(n)
    public static Object dequeue() {
        assert size > 0;
        ensureCapacity();
        size--;
        head++;
        return elements[((head - 1) % elements.length)];
    }

    // Pred: true
    // Post: R = size
    public static int size() {
        return size;
    }

    // Pred: true
    // Post: R = size == 0
    public static boolean isEmpty() {
        return size == 0;
    }

    // Pred: true
    // Post: n = 1 && size = 0
    public static void clear() {
        head = 0;
        size = 0;
    }

    // Pred: true
    // Post: str = "[elements[1], ..., elements[n]]"
    public static String toStr() {
        StringBuilder sb = new StringBuilder("[");
        int i = 0;
        while (i < size) {
            sb.append(elements[(head + i) % elements.length]);
            if (i != size - 1) {
                sb.append(", ");
            }
            i++;
        }
        sb.append(']');
        return sb.toString();
    }

    private static void ensureCapacity() {
        int tail = (head + size) % elements.length;
        if ((head % elements.length) == tail && size == elements.length) {
            Object[] tempHead = Arrays.copyOfRange(elements, 0, tail);
            Object[] tempTail = Arrays.copyOfRange(elements, head % elements.length, elements.length);
            elements = Arrays.copyOf(new Object[1], 2 * elements.length);
            head = elements.length - tempTail.length - tempHead.length;
            System.arraycopy(tempTail, 0, elements, head, tempTail.length);
            System.arraycopy(tempHead, 0, elements, head + tempTail.length, tempHead.length);
        }
    }
}
