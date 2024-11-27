# Producer-Consumer Example with Wait and Notify in Java

## Concept Overview

The **Producer-Consumer problem** is a classic concurrency problem where two threads (the producer and the consumer) communicate via a shared resource. In this case, we model a scenario where a **chef (producer)** prepares orders, and a **waiter (consumer)** picks them up and serves them to customers.

The **`wait()`** and **`notify()`** methods in Java are used for thread communication. These methods enable one thread to wait for a condition to be met, and another thread to notify the waiting thread when the condition is met.

### How `wait()` and `notify()` Work
- **`wait()`**: When a thread calls the `wait()` method, it enters the **waiting state** and releases the lock on the object. The thread remains in this state until another thread calls `notify()` or `notifyAll()` on the same object.
- **`notify()`**: This method wakes up one of the threads that are currently waiting on the object. If multiple threads are waiting, only one will be awakened.
- **`synchronized`**: Both the `wait()` and `notify()` methods must be used inside a synchronized method or block to ensure thread safety. This ensures only one thread can execute the critical section of code at a time.

In our example, the **chef (producer)** prepares an order and notifies the **waiter (consumer)** when the order is ready. The **waiter (consumer)** waits until an order is available and then serves it.

## Problem Description

### Scenario:
- The chef prepares an order and places it on the counter (producer).
- The waiter picks up an order from the counter and delivers it to the customer (consumer).

### Communication:
- The chef (producer) and waiter (consumer) will communicate via an `OrderCounter` shared object.
- The chef will prepare an order, place it on the counter, and notify the waiter.
- The waiter will pick up the order from the counter only when notified by the chef.

## Code Explanation

### OrderCounter Class

The `OrderCounter` class contains two synchronized methods:
- **`placeOrder(int orderNo)`**: This method is called by the chef to place an order on the counter.
- **`pickUpOrder(int orderNo)`**: This method is called by the waiter to pick up the order.

Both methods use the `wait()` and `notify()` methods for thread communication:
- The chef calls `placeOrder()` and notifies the waiter when the order is ready.
- The waiter calls `pickUpOrder()` and waits if there is no order ready.

### Chef Class

The `Chef` class implements `Runnable` and continuously prepares orders, calling `placeOrder()` on the `OrderCounter` object.

### Waiter Class

The `Waiter` class implements `Runnable` and continuously picks up orders, calling `pickUpOrder()` on the `OrderCounter` object.

## Code Example

```java
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
            while (i < 10) {
                counter.placeOrder(i + 1);
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
            while (i < 10) {
                counter.pickUpOrder(i + 1);
                Thread.sleep(1500); // Time to serve the order
                i++;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class ProducerConsumerExample {
    public static void main(String[] args) {
        OrderCounter counter = new OrderCounter();
        Thread chef = new Thread(new Chef(counter));
        Thread waiter = new Thread(new Waiter(counter));
        chef.start();
        waiter.start();
    }
}
```

### Detailed Explanation of the Example:

1. **OrderCounter Class**:
   - The `placeOrder()` method is used by the chef to place an order. If an order is already placed and not picked up (`orderReady == true`), the chef will wait until the waiter picks up the order.
   - The `pickUpOrder()` method is used by the waiter to pick up an order. If no order is ready (`orderReady == false`), the waiter will wait until the chef places an order.

2. **Chef Class**:
   - The chef prepares 10 orders and places them one by one on the counter. After placing an order, the chef notifies the waiter and then sleeps for 1 second before preparing the next order.

3. **Waiter Class**:
   - The waiter picks up 10 orders from the counter. After picking up an order, the waiter notifies the chef and sleeps for 1.5 seconds before picking up the next order.

4. **Thread Communication**:
   - The chef and waiter communicate through the `OrderCounter` object using `wait()` and `notify()` to coordinate when an order is placed and when it is picked up.

## Common Questions

### Can the chef call `pickUpOrder()` if the waiter is waiting?
No, the chef cannot call `pickUpOrder()` because it is a synchronized method. The chef would need to acquire the lock on `OrderCounter` to call `pickUpOrder()`, but since the waiter holds the lock while waiting, the chef cannot call it until the waiter has released the lock.

### How does `wait()` and `notify()` work in this scenario?
- **`wait()`**: When the waiter calls `wait()`, it releases the lock and goes into a waiting state.
- **`notify()`**: When the chef calls `notify()`, it wakes up the waiter, allowing it to recheck the condition and pick up the order if it's ready.

### Is the waiter stuck while calling `wait()`?
No, the waiter is not stuck; it is simply waiting for the order to be ready. The `wait()` method releases the lock, allowing other threads to proceed. The waiter will only resume execution when it is notified by the chef and can acquire the lock again.

## Conclusion

In this example, we demonstrated the **Producer-Consumer problem** using the `wait()` and `notify()` methods for thread communication. The chef and waiter communicate via a shared resource (`OrderCounter`) to ensure that orders are placed and picked up in the correct order. The use of synchronized methods ensures that access to shared resources is controlled, and thread safety is maintained.