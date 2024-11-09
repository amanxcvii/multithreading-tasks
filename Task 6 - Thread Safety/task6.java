// 6. Thread Safety
// Problem: Create a program that demonstrates the use of atomic classes to update a counter in a thread-safe manner.
// Task: Write a Java program where multiple threads increment a shared AtomicInteger counter.


import java.util.concurrent.atomic.AtomicInteger;

/**
 * This program demonstrates the use of AtomicInteger to safely update a counter in a thread-safe manner.
 * Multiple threads are used to increment the counter concurrently.
 * 
 * Author: Aman Malik - amanxcvii
 */
public class task6 {

    public static void main(String[] args) {
        // Create an AtomicInteger counter initialized to 0
        AtomicInteger counter = new AtomicInteger(0);

        // Create 10 threads that will increment the counter
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new IncrementTask(counter));
            threads[i].start(); // Start the thread
        }

        // Wait for all threads to finish
        for (int i = 0; i < 10; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Print the final value of the counter
        System.out.println("Final Counter Value: " + counter.get());
    }
}

/**
 * Task that increments the AtomicInteger counter
 */
class IncrementTask implements Runnable {
    private final AtomicInteger counter;

    public IncrementTask(AtomicInteger counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        // Increment the counter atomically
        for (int i = 0; i < 1000; i++) {
            counter.incrementAndGet();
        }
    }
}
