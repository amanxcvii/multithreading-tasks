// 12. ThreadLocal
// Problem: Use ThreadLocal to maintain a unique identifier for each thread.
// Task: Write a Java program where each thread stores its ID in a ThreadLocal variable and prints it.

public class task12 {
     //ThreadLocal variable holding Integer values, unique for each thread
    private static final ThreadLocal<Integer> threadId = ThreadLocal.withInitial(() -> 0);

    public static void main(String[] args) {
        Runnable task = () -> {
            int id = (int) (Math.random() * 1000);
            threadId.set(id);
            System.out.println(Thread.currentThread().getName() +  "has ThreadLocal ID"  + threadId.get());
        };

        //Create and start multiple threads
        Thread thread1 = new Thread(task, "Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");
        Thread thread3 = new Thread(task, "Thread-3");

        thread1.start();
        thread2.start();
        thread3.start();
    }
}