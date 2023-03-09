package queue;

public class LinkedQueue extends AbstractQueue{
    // Model: a[1]...a[n]
    // Invariant: for i = 1...n: a[i] != null && n >= 0

    // Let immutable(n): for i in 1...n: a[i] == a'[i]
    private Node head;
    private Node tail;

    // Pred: true
    // Post: R = queue
    public static LinkedQueue create() {
        return new LinkedQueue();
    }

    @Override
    protected void enqueueImpl(Object element) {
        if (isEmpty()) {
            this.tail = new Node(element, null);
            this.head = this.tail;
        } else {
            this.tail.next = new Node(element, null);;
            this.tail = this.tail.next;
        }
    }

    @Override
    protected Object elementImpl() {
        return this.head.element;
    }

    @Override
    protected Object dequeueImpl() {
        this.head = this.head.next;
    }

    @Override
    protected void clearImpl() {}

    private static class Node {
         private final Object element;
         private Node next;
         public Node(Object element, Node next) {
             this.element = element;
             this.next = next;
         }
    }
}
