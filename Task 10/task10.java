// 10. Deadlock, Livelock, and Starvation
// Problem: Create a program that simulates a deadlock condition.
// Task: Write a Java program with two threads, each trying to acquire locks on two objects in different orders,
// causing a deadlock. Then, modify the program to resolve the deadlock.

/**
 * Deadlock code as both thread wait for each other to release the lock
 * Thread 1: Holding lock1...
 * Thread 2: Holding lock1...
 * Thread 1: Waiting for lock2...
 * Thread 2: Waiting for lock2...
 * public class task10 {
 * static final Object lock1 = new Object();
 * static final Object lock2 = new Object();
 *
 * public static void main(String[] args) {
 * Thread thread1 = new Thread(() -> {
 * synchronized (lock1) {
 * System.out.println("Thread 1: Holding lock1...");
 * try {
 * Thread.sleep(100);
 * } catch (InterruptedException e) {
 * }
 * System.out.println("Thread 1: Waiting for lock2...");
 * synchronized (lock2) {
 * System.out.println("Thread 1: Acquired lock2!");
 * }
 * }
 * });
 *
 * Thread thread2 = new Thread(() -> {
 * synchronized (lock2) {
 * System.out.println("Thread 2: Holding lock2...");
 * try {
 * Thread.sleep(100);
 * } catch (InterruptedException e) {
 * }
 * System.out.println("Thread 2: Waiting for lock1...");
 * synchronized (lock1) {
 * System.out.println("Thread 2: Acquired lock1!");
 * }
 * }
 * });
 *
 * thread1.start();
 * thread2.start();
 * }
 * }
 **/
public class task10 {
    static final Object lock1 = new Object();
    static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 1: Holding lock1...");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                System.out.println("Thread 1: Waiting for lock2...");
                synchronized (lock2) {
                    System.out.println("Thread 1: Acquired lock2!");
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 2: Holding lock1...");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                System.out.println("Thread 2: Waiting for lock2...");
                synchronized (lock2) {
                    System.out.println("Thread 2: Acquired lock2!");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
// Output:
// Thread 2: Waiting for lock2...
// Thread 2: Acquired lock2!
// Thread 1: Holding lock1...
// Thread 1: Waiting for lock2...
// Thread 1: Acquired lock2!