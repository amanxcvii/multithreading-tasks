// 5. Thread Communication
// Problem: Create a producer-consumer problem where one thread produces data and another thread consumes it.
// Task: Write a Java program using wait() and notify() methods for communication between producer and consumer threads.

// ◘Imagine a restaurant kitchen where chefs prepare meals (producer) and waiters serve them to customers (consumer). In this scenario:

// •The chef prepares an order and places it on the counter (producer).
// •The waiter picks up an order from the counter and delivers it to the customer (consumer).

class OrderCounter {
    private boolean orderReady = false;

    public synchronized void placeOrder(int orderNo) throws InterruptedException {
        while (orderReady) { // Wait until the previous order is picked up
            wait();
        }
        System.out.println("Chef prepared an order no." + orderNo);
        orderReady = true;
        notify(); // Notify waiter that order is ready
    }

    public synchronized void pickUpOrder(int orderNo) throws InterruptedException {
        while (!orderReady) { // Wait if there is no order
            wait();
        }
        System.out.println("Waiter picked up the order." + orderNo);
        orderReady = false;
        notify(); // Notify chef that order is picked up
    }
}

class Chef implements Runnable {
    private final OrderCounter counter;

    Chef(OrderCounter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        try {
            int i = 0;
            while ( i < 10) {
                counter.placeOrder(i+1);
                Thread.sleep(1000); // Time to prepare next order
                i++;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Waiter implements Runnable {
    private final OrderCounter counter;

    Waiter(OrderCounter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        try {
            int i = 0;
            while (i<10) {
                counter.pickUpOrder(i+1);
                Thread.sleep(1500); // Time to serve the order
                i++;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class task5 {
    public static void main(String[] args) {
        OrderCounter counter = new OrderCounter();
        Thread chef = new Thread(new Chef(counter));
        Thread waiter = new Thread(new Waiter(counter));
        chef.start();
        waiter.start();
    }
}
