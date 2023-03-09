package queue;

import java.util.Arrays;
import java.util.Objects;

public class ArrayQueue extends AbstractQueue {
    // Model: a[1]...a[n]
    // Invariant: for i = 1...n: a[i] != null && n >= 0

    // Let immutable(n): for i in 1...n: a[i] == a'[i]
    private int head = 1;

    private Object[] elements = new Object[2];

    // Pred: true
    // Post: R = queue && queue.elements.length = 2
    public static ArrayQueue create() {
        final ArrayQueue queue = new ArrayQueue();
        queue.elements = new Object[2];
        return queue;
    }

    @Override
    protected void enqueueImpl(Object element) {
        ensureCapacity();
        this.elements[(this.head + this.size) % this.elements.length] = element;
    }

    @Override
    protected Object elementImpl() {
        ensureCapacity();
        return this.elements[this.head % this.elements.length];
    }

    @Override
    protected Object dequeueImpl() {
        this.head++;
    }

    @Override
    protected void clearImpl() {
        this.head = 0;
    }

    // Pred: true
    // Post: str = "[a[0], ..., a[n]]"
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

    // Pred: queue != null
    // Post: elements'.length >= elements.length && immutable(n)
    private void ensureCapacity(ArrayQueue this) {
        int tail = (this.head + this.size) % this.elements.length;
        if (this.head % this.elements.length == tail && this.size == this.elements.length) {
            Object[] tempHead = Arrays.copyOfRange(this.elements, 0, tail);
            Object[] tempTail = Arrays.copyOfRange(this.elements, this.head % elements.length, this.elements.length);
            this.elements = Arrays.copyOf(new Object[1], 2 * this.elements.length);
            this.head = this.elements.length - tempTail.length - tempHead.length;
            System.arraycopy(tempTail, 0, this.elements, this.head, tempTail.length);
            System.arraycopy(tempHead, 0, this.elements, this.head + tempTail.length, tempHead.length);
        }
    }
}
