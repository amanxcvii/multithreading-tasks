// 7. Executor Framework
// Problem: Create a program that uses the Executor framework to execute a pool of tasks that calculate the square of a number.
// Task: Write a Java program using ExecutorService to submit and execute a set of tasks that compute and print the square of numbers from 1 to 10.

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class task7 {
    public static void main(String[] args) {
        // Create an ExecutorService with a fixed thread pool of 4 workers
        ExecutorService service = Executors.newFixedThreadPool(4);

        // Submit 10 tasks (representing 10 customer orders)
        for (int orderId = 1; orderId <= 10; orderId++) {
            final int currentOrderId = orderId;
            service.submit(() -> {
                // Simulate the processing of the order
                System.out.println("Processing order #" + currentOrderId + " by worker " + Thread.currentThread().getName());

                try {
                    // Simulate some time taken to process the order (1 second)
                    Thread.sleep(1000); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Reset interrupt flag
                    System.err.println("Order processing was interrupted");
                }

                // After processing, print the confirmation message
                System.out.println("Order #" + currentOrderId + " processed successfully by " + Thread.currentThread().getName());
            });
        }

        // Shutdown the service after all tasks are submitted
        service.shutdown();
    }
}
