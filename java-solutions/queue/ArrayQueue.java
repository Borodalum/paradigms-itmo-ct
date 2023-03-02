package queue;

import java.util.Arrays;
import java.util.Objects;

public class ArrayQueue {
    // Model: a[0]...a[n-1]
    // Invariant: for i = 0...n: a[i] != null && n >= 0

    // Let immutable(n): for i in 0...n: a[i] == a'[i]
    private int size = 0;
    private int head = 1;
    private int tail = 1;

    private Object[] elements = new Object[2];

    public static ArrayQueue create() {
        final ArrayQueue queue = new ArrayQueue();
        queue.elements = new Object[2];
        return queue;
    }

    // Pred: element != null && queue != null;
    // Post: tail' = tail + 1 && a[tail] = element && immutable(n)
    public void enqueue(ArrayQueue this, final Object element) {
        assert element != null;
        Objects.requireNonNull(element);
        cycleIndex();
        this.elements[this.tail] = element;
        this.tail++;
        this.size++;
    }

    // Pred: size > 0 && queue != null
    // Post: R = element && elements[head] = element && element != null && immutable(n)
    public Object element(ArrayQueue this) {
        assert this.size > 0;
        cycleIndex();
        return this.elements[this.head];
    }

    // Pred: size > 0 && queue != null
    // Post: head' = head - 1 && R = element && elements[head] = element && element != null && immutable(n)
    public Object dequeue(ArrayQueue this) {
        assert this.size > 0;
        cycleIndex();
        this.size--;
        return this.elements[this.head++];
    }

    // Pred: queue != null
    // Post: R = size && immutable(n)
    public int size(ArrayQueue this) {
        return this.size;
    }

    // Pred: queue != null
    // Post: R = size == 0 && immutable(n)
    public boolean isEmpty(ArrayQueue this) {
        return this.size == 0;
    }

    // Pred: queue != null
    // Post: head = tail = 1 && size = 0 && immutable(n)
    public void clear(ArrayQueue this) {
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }

    // Pred: true
    // Post: str = "[a[head], ..., a[tail]]"
    public String toStr(ArrayQueue this) {
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

    // Pred: queue != null;
    // Post: 0 <= head, tail < elements.length
    private void cycleIndex(ArrayQueue this) {
        if (this.head == this.elements.length) {
            this.head = 0;
        }
        if (this.tail == this.elements.length) {
            this.tail = 0;
        }
        ensureCapacity();
    }

    // Pred: queue != null
    // Post: elements'.length >= elements.length && immutable(tail, head')
    private void ensureCapacity(ArrayQueue this) {
        if (this.head == this.tail && this.size == this.elements.length) {
            Object[] tempHead = Arrays.copyOfRange(this.elements, 0, this.tail);
            Object[] tempTail = Arrays.copyOfRange(this.elements, this.head, this.elements.length);
            this.elements = Arrays.copyOf(new Object[1], 2 * this.elements.length);
            this.head = this.elements.length - tempTail.length - tempHead.length;
            this.tail = 0;
            System.arraycopy(tempTail, 0, this.elements, this.head, tempTail.length);
            System.arraycopy(tempHead, 0, this.elements, this.head + tempTail.length, tempHead.length);
        }
    }
}
