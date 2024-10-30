// 1. Basics of Threads and Processes
// Problem: Create a program that prints "Hello from Thread X" from multiple threads, where X is the thread number.
// Task: Write a Java program that creates and starts 5 threads, each printing a unique message.
public class task1 {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            int val = i;
            Thread thread = new Thread(() -> {
                System.out.println("Thread"+val+" is running");
                // System.out.println(Thread.currentThread().getName());
            });
            thread.start();
        }
        System.out.println(Thread.currentThread().getName());
    }
}
