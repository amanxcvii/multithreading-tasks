// 11. Advanced Locking Techniques
// Problem: Create a program that uses a ReadWriteLock to manage read and write access to a shared resource.
// Task: Write a Java program where multiple threads read from and write to a shared data structure using ReentrantReadWriteLock.

import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class task11 {
    private final Stack<Integer> sharedStack = new Stack<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    private static final int MAX_SIZE = 10; // Limit stack size

    // Method for popping the top element from the stack
    public void popData() {
        writeLock.lock(); // Modify operation requires write lock
        try {
            if (!sharedStack.isEmpty()) {
                int value = sharedStack.pop();
                System.out.println(Thread.currentThread().getName() + " popped: " + value);
            } else {
                System.out.println(Thread.currentThread().getName() + " found stack empty.");
            }
        } finally {
            writeLock.unlock();
        }
    }

    // Method for pushing data onto the stack
    public void pushData() {
        writeLock.lock();
        try {
            if (sharedStack.size() < MAX_SIZE) {
                int value = ThreadLocalRandom.current().nextInt(100);
                sharedStack.push(value);
                System.out.println(Thread.currentThread().getName() + " pushed: " + value);
            } else {
                System.out.println(Thread.currentThread().getName() + " stack is full, skipping push.");
            }
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        StackWithReadWriteLockExample example = new StackWithReadWriteLockExample();
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // Reader task (pops from the stack)
        Runnable popperTask = () -> {
            for (int i = 0; i < 10; i++) {
                example.popData();
                sleepRandomly(100, 300);
            }
        };

        // Writer task (pushes to the stack)
        Runnable pusherTask = () -> {
            for (int i = 0; i < 10; i++) {
                example.pushData();
                sleepRandomly(200, 500);
            }
        };

        // Submit tasks to executor
        for (int i = 0; i < 2; i++) executor.submit(popperTask); // 2 poppers
        for (int i = 0; i < 2; i++) executor.submit(pusherTask); // 2 pushers

        executor.shutdown();
    }

    // Utility method for random sleep
    private static void sleepRandomly(int minMillis, int maxMillis) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(minMillis, maxMillis));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
