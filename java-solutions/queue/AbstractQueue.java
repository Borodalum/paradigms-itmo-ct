package queue;

import java.util.Objects;

public abstract class AbstractQueue implements Queue {
    protected int size;

    // Pred: element != null && queue != null;
    // Post: n' = n + 1 && elements[n'] = element && immutable(n')
    public void enqueue(AbstractQueue this, final Object element) {
        assert element != null;
        Objects.requireNonNull(element);
        enqueueImpl(element);
        this.size++;
    }

    protected abstract void enqueueImpl(Object element);

    // Pred: n > 0 && queue != null
    // Post: R = element && elements[1] = element && element != null && immutable(n)
    public Object element(AbstractQueue this) {
        assert this.size > 0;
        return elementImpl();
    }

    protected abstract Object elementImpl();

    // Pred: n > 0 && queue != null
    // Post: n' = n - 1 && R = element && elements[1] = element && element != null && immutable(n)
    public Object dequeue(AbstractQueue this) {
        assert this.size > 0;
        this.size--;
        return dequeueImpl();
    }

    protected abstract Object dequeueImpl();

    // Pred: queue != null
    // Post: n = 0
    public void clear(AbstractQueue this) {
        clearImpl();
        this.size = 0;
    }

    protected abstract void clearImpl();

    // Pred: queue != null
    // Post: R = n && immutable(n)
    public int size(AbstractQueue this) {
        return this.size;
    }

    // Pred: queue != null
    // Post: R = (n == 0) && immutable(n)
    public boolean isEmpty(AbstractQueue this) {
        return this.size == 0;
    }

    // Pred: element != null
    // Post: count of element in elements[] && immutable(n)
    public int count(AbstractQueue this, Object element) {
        int result = 0;
        int i = 0;
        while (i < size) {
            if (element.equals(element())) {
                result++;
            }
            enqueue(dequeue());
            i++;
        }
        return result;
    }
}
