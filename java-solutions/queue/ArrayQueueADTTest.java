package queue;

import static queue.ArrayQueueADT.*;

public class ArrayQueueADTTest {
    public static void main(String[] args) {
        ArrayQueueADT queue1 = create();
        ArrayQueueADT queue2 = create();
        for (int i = 0; i < 5; i++) {
            enqueue(queue1, "1-elem" + i);
            enqueue(queue2, "2-elem" + i);
        }
        System.out.println(toStr(queue1));
        System.out.println(toStr(queue2));
        System.out.println("Size queue1 is " + size(queue1));
        System.out.println("Size queue2 is " + size(queue2));
        dumpQueue(queue1);
        dumpQueue(queue2);
    }

    private static void dumpQueue(ArrayQueueADT queue) {
        while (!isEmpty(queue)) {
            System.out.println(size(queue) + " : " + dequeue(queue));
            System.out.println("Next elem is " + element(queue));
        }
    }
}
