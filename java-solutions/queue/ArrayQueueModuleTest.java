package queue;

public class ArrayQueueModuleTest {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            ArrayQueueModule.enqueue("elem" + i);
        }
        System.out.println(ArrayQueueModule.toStr());
        System.out.println("Size is " + ArrayQueueModule.size());
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(ArrayQueueModule.size() + " : " + ArrayQueueModule.dequeue());
            System.out.println("Next elem is " + ArrayQueueModule.element());
        }
    }
}
