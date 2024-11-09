# Understanding Race Condition and Thread Safety in Java

In this document, we will explain the concept of race conditions in multi-threaded environments and show an example of how to safely update a shared counter using Java's `AtomicInteger`. We will also explore a **wrong example** where a race condition occurs due to the improper use of `Integer`.

## Problem

When multiple threads concurrently modify a shared resource, and the modification is not done atomically, it can lead to **race conditions**. This results in unpredictable and incorrect behavior, especially when threads are reading and writing shared data at the same time.

In this document, we will:

1. Show how race conditions occur when using `Integer` in a multi-threaded environment.
2. Demonstrate the correct approach with `AtomicInteger`, which ensures thread safety.

## Wrong Example: Using `Integer` (Race Condition)

In this example, multiple threads are incrementing a counter stored as an `Integer`. Since `Integer` is immutable and the increment operation (`counter++`) is not atomic, this leads to a race condition. Threads may overwrite each other's updates, causing lost increments.

### Code Example with Race Condition:

```java
/**
 * This program demonstrates a race condition when using Integer to update a counter in a multi-threaded environment.
 * The counter is incremented by multiple threads, leading to inconsistent and incorrect results due to the race condition.
 * 
 * Author: Aman Malik - amanxcvii
 */
public class Task6WithRaceCondition {

    public static void main(String[] args) {
        // Create an Integer counter initialized to 0 (this will not work as expected in multi-threaded environment)
        Integer counter = 0;
        IncrementTask task = new IncrementTask(counter);

        // Create 10 threads that will increment the counter
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(task);
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

        // Print the final value of the counter (Expected: 10000, but will be incorrect due to race condition)
        System.out.println("Final Counter Value: " + task.getCount());
    }
}

/**
 * Task that increments the Integer counter (this causes a race condition)
 */
class IncrementTask implements Runnable {
    private Integer counter;

    public IncrementTask(Integer counter) {
        this.counter = counter;
    }

    // Method to get the current count value
    public Integer getCount() {
        return counter;
    }

    @Override
    public void run() {
        // Increment the counter (this is where the race condition occurs)
        for (int i = 0; i < 1000; i++) {
            counter++;  // Non-atomic operation, leading to race condition
        }
    }
}
```

Why Does the Race Condition Occur?

    Integer Immutability: The Integer class in Java is immutable, which means that once an Integer object is created, its value cannot be changed. The counter++ operation actually performs the following steps:
        Read the current value of counter.
        Increment it.
        Write the incremented value back to counter.

    Since this operation is not atomic and threads are running concurrently, this can lead to a situation where multiple threads read the same value before writing the incremented result back. This results in lost increments and an incorrect final value.

Expected Output (with Race Condition):

Since multiple threads are modifying the counter concurrently, the final value will be lower than expected. The expected value should be 10000 (10 threads × 1000 increments), but due to the race condition, the result will be inconsistent.

Final Counter Value: 6543  // Example output, will vary depending on thread execution timing

Correct Example: Using AtomicInteger (Thread Safe)

To resolve the issue of race conditions, we can use AtomicInteger, which provides thread-safe atomic operations for updating the counter. The AtomicInteger class ensures that operations like incrementAndGet() are done atomically, meaning that no other thread can interfere during the update.

## Code Example with AtomicInteger:

```java
import java.util.concurrent.atomic.AtomicInteger;


/**
 * This program demonstrates the use of AtomicInteger to safely update a counter in a thread-safe manner.
 * Multiple threads are used to increment the counter concurrently.
 * 
 * Author: Aman Malik - amanxcvii
 */
public class Task6WithAtomicInteger {

    public static void main(String[] args) {
        // Create an AtomicInteger counter initialized to 0
        AtomicInteger counter = new AtomicInteger(0);
        IncrementTask task = new IncrementTask(counter);

        // Create 10 threads that will increment the counter
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(task);
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

        // Print the final value of the counter (Expected: 10000)
        System.out.println("Final Counter Value: " + task.getCount());
    }
}

/**
 * Task that increments the AtomicInteger counter
 */
class IncrementTask implements Runnable {
    private AtomicInteger counter;

    public IncrementTask(AtomicInteger counter) {
        this.counter = counter;
    }

    // Method to get the current count value
    public int getCount() {
        return counter.get();
    }

    @Override
    public void run() {
        // Increment the counter atomically
        for (int i = 0; i < 1000; i++) {
            counter.incrementAndGet();  // Atomic operation, thread-safe
        }
    }
}
```
Why AtomicInteger Works:

    AtomicInteger provides methods like incrementAndGet() that ensure the operation is performed atomically. This means that no other thread can access the counter while it is being incremented, preventing race conditions.
    The incrementAndGet() method performs the increment in a single atomic operation, ensuring that each increment is done safely.

Expected Output (with AtomicInteger):

With AtomicInteger, the final value of the counter will always be correct, as each thread performs the increment atomically.

Final Counter Value: 10000  // Correct value, no race condition

Conclusion

This document demonstrated the difference between a wrong approach (using Integer in a multi-threaded environment) and the correct approach (using AtomicInteger) to handle concurrent updates to a shared counter in a thread-safe manner. By using AtomicInteger, we can avoid race conditions and ensure that the counter is incremented correctly, even when multiple threads are involved.
Key Takeaways:

    Race Condition: Occurs when multiple threads concurrently access and modify shared data without proper synchronization.
    Integer Problem: Integer is immutable, and the increment operation (counter++) is not atomic, causing race conditions.
    AtomicInteger Solution: Provides thread-safe atomic methods like incrementAndGet() to safely modify shared data.

Author: Aman Malik - amanxcvii

