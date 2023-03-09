package queue;

public interface Queue {
    // Model: a[1]...a[n]
    // Invariant: for i = 1...n: a[i] != null && n >= 0
    // Let immutable(n): for i in 1...n: a[i] == a'[i]

    // Pred: true
    // Post: R = n && immutable(n)
    int size();
    // Pred: true
    // Post: R = (n == 0) && immutable(n)
    boolean isEmpty();
    // Pred: n > 0
    // Post: n' = n - 1 && R = element && elements[1] = element && element != null && immutable(n')
    Object dequeue();
    // Pred: element != null
    // Post: n' = n + 1 && elements[n'] = element && immutable(n')
    void enqueue(Object element);
    // Pred: n > 0
    // Post: R = element && elements[1] = element && element != null && immutable(n)
    Object element();
    // Pred: true
    // Post: n = 0
    void clear();
    // Pred: element != null
    // Post: count of element in a && immutable(n)
    int count(Object element);
}
