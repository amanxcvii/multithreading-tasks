// 13. Java Memory Model (JMM)
// Problem: Demonstrate the use of the volatile keyword to ensure visibility of changes to a shared variable across threads.
// Task: Write a Java program with a shared volatile boolean variable that one thread updates and another thread reads, demonstrating the visibility of the update.

public class task13 {

    // Shared volatile variable
    private static volatile boolean running = true;

    public static void main(String[] args) {

        // Reader Thread - keeps running while 'running' is true
        Thread readerThread = new Thread(() -> {
            System.out.println("Reader Thread started...");
            while (running) {
                // Busy-wait until 'running' becomes false
            }
            System.out.println("Reader Thread detected change and stopped.");
        });

        // Writer Thread - sleeps for 2 seconds and then changes 'running' to false
        Thread writerThread = new Thread(() -> {
            try {
                Thread.sleep(2000); // Simulate some work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            running = false;
            System.out.println("Writer Thread updated 'running' to false.");
        });

        readerThread.start();
        writerThread.start();
    }
}
