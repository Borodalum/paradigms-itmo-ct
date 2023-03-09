package queue;

public class QueueTestMy {
    public static void main(String[] args) {
        Queue queue1 = new LinkedQueue();
        Queue queue2 = new ArrayQueue();
        for (int i = 0; i < 5; i++) {
            queue1.enqueue("1-elem" + i);
            queue2.enqueue("2-elem" + i);
        }
        System.out.println("Size queue1 is " + queue1.size());
        System.out.println("Size queue2 is " + queue2.size());
        dumpQueue(queue1);
        dumpQueue(queue2);
    }

    private static void dumpQueue(Queue queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.size() + " : " + queue.dequeue());
            System.out.println("Next elem is " + queue.element());
        }
    }
}
