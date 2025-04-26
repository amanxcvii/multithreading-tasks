# Java Memory Model (JMM) - Volatile Keyword Demonstration

## Overview

This project demonstrates the use of the **`volatile` keyword** in Java to ensure **visibility** of changes made to a shared variable across multiple threads.

It highlights how **without volatile**, one thread’s updates may not be immediately visible to other threads due to **thread caching** or **instruction reordering**, and how `volatile` solves this by enforcing memory visibility guarantees according to the **Java Memory Model (JMM)**.

---

### Key Concepts Covered:
- **Java Memory Model (JMM)** basics
- **Visibility Problems** in multithreading
- **Volatile Keyword** ensuring up-to-date reads between threads
- **Thread Communication** through shared variables

---

## Problem: Visibility Issues in Multithreaded Programs

In a multithreaded program, changes made by one thread to a shared variable **may not be visible** immediately to other threads.  
This can lead to **stale reads** and **unexpected behavior**.

**Solution**: Mark the shared variable as `volatile`, which ensures:
- Updates by one thread are immediately visible to other threads.
- No thread will cache the value locally.
- Certain types of reordering optimizations are prevented.

---

## Program Structure

1. **Shared Volatile Boolean Flag**: `volatile boolean running = true;`
2. **Writer Thread**: Changes the flag after a short delay.
3. **Reader Thread**: Continuously checks the flag to decide whether to continue running.

---

## Example Code

```java
public class VolatileExample {

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
```

---

## How It Works

1. **Initially**, `running` is `true`, and the reader thread continuously loops.
2. **After 2 seconds**, the writer thread sets `running = false`.
3. **Because `running` is volatile**, the reader thread **immediately sees the change** and exits the loop.
4. Without `volatile`, the reader thread **might never see the update** and could loop forever!

---


## Example Output

```
Reader Thread started...
Writer Thread updated 'running' to false.
Reader Thread detected change and stopped.
```

---

## Important Observations

| Without `volatile`          | With `volatile`               |
|------------------------------|-------------------------------|
| Reader might loop forever   | Reader stops after update     |
| Stale data cached by thread | Fresh value fetched every time |
| Potential deadlocks         | Correct and timely communication |

---

## Why Is Volatile Important?

- Guarantees **visibility** but **does not guarantee atomicity** (e.g., not safe for read-modify-write operations).
- Suitable for **simple flags** and **state indicators** between threads.
- Reduces the need for **heavyweight synchronization** for basic communications.

> ⚠️ Note: For compound actions (read + write together), use `synchronized` or atomic classes (e.g., `AtomicInteger`), not just `volatile`.

---

## Key Learning Outcomes

- Understand **visibility** vs. **atomicity** in concurrency.
- Learn **when to use `volatile`** effectively.
- Recognize **Java Memory Model's role** in thread-safe programming.

---

## Related Topics for Deeper Study

- Happens-before relationships in JMM
- Instruction reordering and memory barriers
- Atomic variables (`AtomicBoolean`, `AtomicInteger`, etc.)
- Synchronization vs Volatile: when to use which?

---

## Author
```
Aman Malik - amanxcvii
```
