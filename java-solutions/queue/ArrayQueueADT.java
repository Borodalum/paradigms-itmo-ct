package queue;

import java.util.Arrays;
import java.util.Objects;

public class ArrayQueueADT {
    // Model: a[0]...a[n-1]
    // Invariant: for i = 0...n: a[i] != null && n >= 0

    // Let immutable(n): for i in 0...n: a[i] == a'[i]
    private int size = 0;
    private int head = 1;
    private int tail = 1;

    private Object[] elements = new Object[2];

    public static ArrayQueueADT create() {
        ArrayQueueADT stack = new ArrayQueueADT();
        stack.elements = new Object[10];
        return stack;
    }

    // Pred: element != null && queue != null;
    // Post: tail' = tail + 1 && a[tail] = element && immutable(tail, head)
    public static void enqueue(final ArrayQueueADT queue, final Object element) {
        assert element != null;
        Objects.requireNonNull(element);
        cycleIndex(queue);
        queue.elements[queue.tail] = element;
        queue.tail++;
        queue.size++;
    }

    // Pred: size > 0 && queue != null
    // Post: R = element && elements[head] = element && element != null && immutable(tail, head)
    public static Object element(final ArrayQueueADT queue) {
        assert queue.size > 0;
        cycleIndex(queue);
        return queue.elements[queue.head];
    }

    // Pred: size > 0 && queue != null
    // Post: head' = head - 1 && R = element && elements[head] = element && element != null && immutable(tail, head')
    public static Object dequeue(final ArrayQueueADT queue) {
        assert queue.size > 0;
        cycleIndex(queue);
        queue.size--;
        return queue.elements[queue.head++];
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
    // Post: head = tail = 1 && size = 0
    public static void clear(final ArrayQueueADT queue) {
        queue.head = 0;
        queue.tail = 0;
        queue.size = 0;
    }

    // Pred: true
    // Post: str = "[a[head], ..., a[tail]]"
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

    // Pred: queue != null;
    // Post: 0 <= head, tail < elements.length
    private static void cycleIndex(final ArrayQueueADT queue) {
        if (queue.head == queue.elements.length) {
            queue.head = 0;
        }
        if (queue.tail == queue.elements.length) {
            queue.tail = 0;
        }
        ensureCapacity(queue);
    }

    // Pred: queue != null
    // Post: elements'.length >= elements.length && immutable(tail, head')
    private static void ensureCapacity(final ArrayQueueADT queue) {
        if (queue.head == queue.tail && queue.size == queue.elements.length) {
            Object[] tempHead = Arrays.copyOfRange(queue.elements, 0, queue.tail);
            Object[] tempTail = Arrays.copyOfRange(queue.elements, queue.head, queue.elements.length);
            queue.elements = Arrays.copyOf(new Object[1], 2 * queue.elements.length);
            queue.head = queue.elements.length - tempTail.length - tempHead.length;
            queue.tail = 0;
            System.arraycopy(tempTail, 0, queue.elements, queue.head, tempTail.length);
            System.arraycopy(tempHead, 0, queue.elements, queue.head + tempTail.length, tempHead.length);
        }
    }
}
