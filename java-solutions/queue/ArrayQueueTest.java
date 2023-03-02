package queue;

import static queue.ArrayQueue.*;

public class ArrayQueueTest {
    public static void main(String[] args) {
        ArrayQueue queue1 = create();
        ArrayQueue queue2 = create();
        for (int i = 0; i < 5; i++) {
            queue1.enqueue("1-elem" + i);
            queue2.enqueue("2-elem" + i);
        }
        System.out.println(queue1.toStr());
        System.out.println(queue2.toStr());
        System.out.println("Size queue1 is " + queue1.size());
        System.out.println("Size queue2 is " + queue2.size());
        dumpQueue(queue1);
        dumpQueue(queue2);
    }

    private static void dumpQueue(ArrayQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.size() + " : " + queue.dequeue());
            System.out.println("Next elem is " + queue.element());
        }
    }
}
