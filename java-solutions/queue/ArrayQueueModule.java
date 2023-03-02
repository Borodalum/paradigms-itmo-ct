package queue;

import java.util.Arrays;
import java.util.Objects;

public class ArrayQueueModule {
    // Model: a[0]...a[n-1]
    // Invariant: for i = 0...n: a[i] != null && n >= 0

    // Let immutable(n): for i in 0...n: a[i] == a'[i]
    private static int size = 0;
    private static int head = 1;
    private static int tail = 1;

    private static Object[] elements = new Object[2];

    // Pred: element != null;
    // Post: tail' = tail + 1 && a[tail] = element && immutable(n)
    public static void enqueue(final Object element) {
        assert element != null;
        Objects.requireNonNull(element);
        cycleIndex();
        elements[tail] = element;
        tail++;
        size++;
    }

    // Pred: size > 0
    // Post: R = element && elements[head] = element && element != null && immutable(n)
    public static Object element() {
        assert size > 0;
        cycleIndex();
        return elements[head];
    }

    // Pred: size > 0
    // Post: head' = head - 1 && R = element && elements[head] = element && element != null && immutable(n)
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
    // Post: head = tail = 1 && size = 0
    public static void clear() {
        head = 0;
        tail = 0;
        size = 0;
    }

    // Pred: true
    // Post: str = "[a[head], ..., a[tail]]"
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
    // Post: 0 <= head, tail < elements.length
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
    // Post: elements'.length >= elements.length && immutable(tail, head')
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
