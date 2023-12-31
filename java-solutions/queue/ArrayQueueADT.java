package queue;

import java.util.Arrays;
import java.util.Objects;

public class ArrayQueueADT {
    // Model: a[1]...a[n]
    // Invariant: for i = 1...n: a[i] != null && n >= 0

    // Let immutable(n): for i in 1...n: a[i] == a'[i]
    private int size = 0;
    private int head = 0;

    private Object[] elements = new Object[2];

    // Pred: true
    // Post: R = queue && queue.elements.length = 2
    public static ArrayQueueADT create() {
        ArrayQueueADT queue = new ArrayQueueADT();
        queue.elements = new Object[2];
        return queue;
    }

    // Pred: element != null && queue != null;
    // Post: n' = n + 1 && elements[n] = element && immutable(n)
    public static void enqueue(final ArrayQueueADT queue, final Object element) {
        assert element != null;
        Objects.requireNonNull(element);
        ensureCapacity(queue);
        queue.elements[(queue.head + queue.size) % queue.elements.length] = element;
        queue.size++;
    }

    // Pred: size > 0 && queue != null
    // Post: R = element && elements[0] = element && element != null && immutable(n)
    public static Object element(final ArrayQueueADT queue) {
        assert queue.size > 0;
        ensureCapacity(queue);
        return queue.elements[queue.head % queue.elements.length];
    }

    // Pred: size > 0 && queue != null
    // Post: n' = n - 1 && R = element && elements[0] = element && element != null && immutable(n)
    public static Object dequeue(final ArrayQueueADT queue) {
        assert queue.size > 0;
        ensureCapacity(queue);
        queue.size--;
        queue.head++;
        return queue.elements[(queue.head - 1) % queue.elements.length];
    }

    // Pred: queue != null
    // Post: R = size
    public static int size(final ArrayQueueADT queue) {
        return queue.size;
    }

    // Pred: queue != null
    // Post: R = size == 0
    public static boolean isEmpty(final ArrayQueueADT queue) {
        return queue.size == 0;
    }

    // Pred: queue != null
    // Post: n = 0 && size = 0
    public static void clear(final ArrayQueueADT queue) {
        queue.head = 0;
        queue.size = 0;
    }

    // Pred: true
    // Post: str = "[a[0], ..., a[n]]"
    public static String toStr(final ArrayQueueADT queue) {
        StringBuilder sb = new StringBuilder("[");
        int i = 0;
        while (i < queue.size) {
            sb.append(queue.elements[(queue.head + i) % queue.elements.length]);
            if (i != queue.size - 1) {
                sb.append(", ");
            }
            i++;
        }
        sb.append(']');
        return sb.toString();
    }

    // Pred: queue != null
    // Post: elements'.length >= elements.length  && immutable(n)
    private static void ensureCapacity(final ArrayQueueADT queue) {
        int tail = (queue.head + queue.size) % queue.elements.length;
        if (queue.head % queue.elements.length == tail && queue.size == queue.elements.length) {
            Object[] tempHead = Arrays.copyOfRange(queue.elements, 0, tail);
            Object[] tempTail = Arrays.copyOfRange(queue.elements, queue.head % queue.elements.length, queue.elements.length);
            queue.elements = Arrays.copyOf(new Object[1], 2 * queue.elements.length);
            queue.head = queue.elements.length - tempTail.length - tempHead.length;
            System.arraycopy(tempTail, 0, queue.elements, queue.head, tempTail.length);
            System.arraycopy(tempHead, 0, queue.elements, queue.head + tempTail.length, tempHead.length);
        }
    }
}
