package queue;

import java.util.Arrays;
import java.util.Objects;

public class ArrayQueueModule {
    // Model: a[1]...a[n]
    // Invariant: for i = 1...n: a[i] != null && n >= 0

    // Let immutable(n): for i in 1...n: a[i] == a'[i]
    private static int size = 0;
    private static int head = 1;
    private static int tail = 1;

    private static Object[] elements = new Object[2];

    // Pred: element != null;
    // Post: n' = n + 1 && a[0] = element && immutable(n)
    public static void enqueue(final Object element) {
        assert element != null;
        Objects.requireNonNull(element);
        cycleIndex();
        elements[tail] = element;
        tail++;
        size++;
    }

    // Pred: size > 0
    // Post: R = element && elements[0] = element && element != null && immutable(n)
    public static Object element() {
        assert size > 0;
        cycleIndex();
        return elements[head];
    }

    // Pred: size > 0
    // Post: n' = n - 1 && R = element && elements[0] = element && element != null && immutable(n)
    public static Object dequeue() {
        assert size > 0;
        cycleIndex();
        size--;
        return elements[head++];
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
        tail = 0;
        size = 0;
    }

    // Pred: true
    // Post: str = "[a[0], ..., a[n]]"
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

    // Pred: true;
    // Post: 0 <= n < elements.length
    private static void cycleIndex() {
        if (head == elements.length) {
            head = 0;
        }
        if (tail == elements.length) {
            tail = 0;
        }
        ensureCapacity();
    }

    // Pred: true
    // Post: elements'.length >= elements.length && immutable(n)
    private static void ensureCapacity() {
        if (head == tail && size == elements.length) {
            Object[] tempHead = Arrays.copyOfRange(elements, 0, tail);
            Object[] tempTail = Arrays.copyOfRange(elements, head, elements.length);
            elements = Arrays.copyOf(new Object[1], 2 * elements.length);
            head = elements.length - tempTail.length - tempHead.length;
            tail = 0;
            System.arraycopy(tempTail, 0, elements, head, tempTail.length);
            System.arraycopy(tempHead, 0, elements, head + tempTail.length, tempHead.length);
        }
    }
}
