# Understanding Deadlock

## Overview

This project demonstrates a **deadlock** scenario in Java, which occurs when two or more threads are unable to proceed because each thread is waiting for the other to release a lock. The program simulates a classic deadlock condition where two threads attempt to acquire locks on two shared objects in reverse order, resulting in a situation where both threads are blocked and cannot proceed.

### Key Concepts Covered:
- **Deadlock**: A concurrency issue where threads are stuck in a circular wait.
- **Locks and Synchronization**: Managing shared resources between multiple threads using locks.
- **Threading Issues**: Understanding how thread interlocks can lead to system inefficiencies or application crashes.

## Problem: Deadlock Scenario

In this program, **Thread 1** and **Thread 2** both try to acquire locks on two objects (`lock1` and `lock2`), but they acquire them in reverse orders. This results in a **deadlock**, where both threads are waiting indefinitely for each other to release the lock they need.

- **Thread 1** acquires `lock1`, then tries to acquire `lock2`.
- **Thread 2** acquires `lock2`, then tries to acquire `lock1`.
- Both threads are blocked because each is waiting for the other to release the lock it needs, causing a deadlock.

## Program Structure

1. **Two Threads**: The program creates two threads, each attempting to lock two objects in different orders.
2. **Synchronized Blocks**: `synchronized` blocks are used to ensure that only one thread can access a particular lock at a time.
3. **Deadlock Creation**: The deadlock occurs because the threads are attempting to acquire locks in opposite orders, leading to a situation where each thread is holding one lock and waiting for the other.

### Example Deadlock Code:

```java
public class DeadlockExample {
    // Shared lock objects
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        // Thread 1 acquires lock1 first, then lock2
        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 1: Acquired lock1");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                synchronized (lock2) {
                    System.out.println("Thread 1: Acquired lock2");
                }
            }
        });

        // Thread 2 acquires lock2 first, then lock1
        Thread thread2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("Thread 2: Acquired lock2");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                synchronized (lock1) {
                    System.out.println("Thread 2: Acquired lock1");
                }
            }
        });

        // Start both threads
        thread1.start();
        thread2.start();
    }
}
```

## How It Works

### 1. **Thread 1**: 
   - First acquires `lock1`.
   - Then, after a short delay, tries to acquire `lock2`.
   
### 2. **Thread 2**: 
   - First acquires `lock2`.
   - Then, after a short delay, tries to acquire `lock1`.
   
### 3. **Deadlock**:
   - At this point, **Thread 1** holds `lock1` and is waiting for `lock2`, while **Thread 2** holds `lock2` and is waiting for `lock1`.
   - Both threads are stuck, waiting for the other to release a lock, leading to a **deadlock** situation.

## Steps to Run the Program

1. **Clone or Download the Repository**:

2. **Compile the Java Program**:

3. **Run the Program**:

4. **Observe the Output**:
   - The program will print "Thread 1: Acquired lock1" and "Thread 2: Acquired lock2" at the beginning.
   - However, the program will **freeze** because both threads are waiting on each other, resulting in a deadlock.

## Example Output

```
Thread 1: Acquired lock1
Thread 2: Acquired lock2
```

- The program will not proceed further, as both threads are now deadlocked, waiting for the other to release a lock.

## Resolving Deadlock

Deadlock can be resolved by ensuring that both threads acquire locks in the same order. Here's how you can fix the deadlock:

### Solution:

- Both threads should acquire `lock1` first and then `lock2`, ensuring a consistent locking order.

### Modified Code to Prevent Deadlock:

```java
public class DeadlockExample {
    // Shared lock objects
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        // Thread 1 acquires lock1 first, then lock2
        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 1: Acquired lock1");
                synchronized (lock2) {
                    System.out.println("Thread 1: Acquired lock2");
                }
            }
        });

        // Thread 2 acquires lock1 first, then lock2 (same order as Thread 1)
        Thread thread2 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 2: Acquired lock1");
                synchronized (lock2) {
                    System.out.println("Thread 2: Acquired lock2");
                }
            }
        });

        // Start both threads
        thread1.start();
        thread2.start();
    }
}
```

With this change, both threads will acquire locks in the same order, avoiding the circular wait and thus preventing the deadlock.

## Key Concepts and Learning Outcomes

1. **Deadlock**: Understanding how and why deadlocks happen in multithreading programs.
2. **Thread Synchronization**: Using `synchronized` blocks to control access to shared resources.
3. **Locking Order**: Ensuring that threads acquire locks in a consistent order to avoid deadlocks.
4. **Concurrency Pitfalls**: Learning how to avoid common pitfalls in concurrent programming, such as deadlocks, race conditions, and thread contention.

## DeadLock vs Other Components

- **Deadlock** occurs when two or more threads are blocked forever, waiting for each other to release resources.
- **Starvation** occurs when a thread is perpetually denied access to resources because other threads are always given priority.
- **Livelock** is a situation where threads are actively trying to acquire resources but continually prevent each other from progressing, often appearing as if the system is working but nothing gets done.

### Key Concepts Covered:
- **Deadlock**: A situation where two or more threads are stuck, waiting for each other indefinitely.
- **Starvation**: A condition where one or more threads are never given access to the required resources.
- **Livelock**: A situation where threads are actively running but unable to make progress due to continually preventing each other.

## Problem 1: Deadlock

### Deadlock Scenario

In this program, **Thread 1** and **Thread 2** both try to acquire locks on two objects (`lock1` and `lock2`), but they acquire them in reverse orders. This creates a circular wait, leading to a **deadlock**.

## Problem 2: Starvation

### Starvation Scenario

In **starvation**, a thread is perpetually blocked because other threads continually acquire the resources it needs. For instance, if a low-priority thread keeps being preempted by higher-priority threads, it may never get a chance to execute.

### Starvation Code Example

```java
public class StarvationExample {
    public static void main(String[] args) {
        Thread highPriorityThread = new Thread(() -> {
            synchronized (this) {
                System.out.println("High-priority thread executing...");
            }
        });

        Thread lowPriorityThread = new Thread(() -> {
            synchronized (this) {
                System.out.println("Low-priority thread executing...");
            }
        });

        // Set low priority for the low-priority thread
        lowPriorityThread.setPriority(Thread.MIN_PRIORITY);

        highPriorityThread.start();
        lowPriorityThread.start();
    }
}
```

### Explanation

- In this case, the **high-priority thread** runs repeatedly, and the **low-priority thread** may never get a chance to execute, resulting in **starvation** for the low-priority thread.

### Output

```
High-priority thread executing...
High-priority thread executing...
...
(Repeated high-priority execution)
```

- The **low-priority thread** may never run if the system is always giving resources to the **high-priority thread**.

## Problem 3: Livelock

### Livelock Scenario

In **livelock**, two or more threads continually change their states in response to each other, preventing each other from making progress. Unlike deadlock, threads in a livelock are actively executing, but no real work is done.

### Livelock Code Example

```java
public class LivelockExample {
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            while (true) {
                synchronized (lock1) {
                    System.out.println("Thread 1: Acquired lock1");
                    synchronized (lock2) {
                        System.out.println("Thread 1: Acquired lock2");
                        break;
                    }
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (true) {
                synchronized (lock2) {
                    System.out.println("Thread 2: Acquired lock2");
                    synchronized (lock1) {
                        System.out.println("Thread 2: Acquired lock1");
                        break;
                    }
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
```

### Explanation

- Both **Thread 1** and **Thread 2** are acquiring locks and releasing them repeatedly in a cycle, but neither thread makes progress because they keep trying to acquire the second lock when the other thread is already holding it.

### Output

```
Thread 1: Acquired lock1
Thread 2: Acquired lock2
Thread 2: Acquired lock1
Thread 1: Acquired lock2
...
```

- The threads appear to be making progress, but no actual work is done, resulting in a **livelock**.

## Deadlock, Livelock, and Starvation: Key Differences

| **Concept**   | **Description**                                                                            | **Threads' Status**            | **Example**                                     |
|---------------|--------------------------------------------------------------------------------------------|--------------------------------|-------------------------------------------------|
| **Deadlock**  | Two or more threads are blocked forever, waiting on each other to release resources.      | Blocked (waiting indefinitely) | Two threads trying to acquire locks in reverse order (as shown in the Deadlock example). |
| **Starvation**| A thread is perpetually denied access to resources due to high-priority threads running.   | Active but not making progress | A low-priority thread being blocked by high-priority threads (as shown in the Starvation example). |
| **Livelock**  | Threads are actively executing but are unable to make progress due to continually changing their states. | Actively running but not progressing | Two threads continually releasing and acquiring locks (as shown in the Livelock example). |

## How to Fix Deadlock, Starvation, and Livelock

### Deadlock Prevention
- **Avoid circular waits** by acquiring locks in the same order across all threads.
  
### Starvation Prevention
- **Fair locking mechanisms** like `ReentrantLock` with fairness set to `true` can ensure that all threads get a chance to execute.
  
### Livelock Prevention
- **Backoff strategies** or retries with delay can prevent the system from endlessly cycling through the same set of actions without making progress.

## Author
```
Aman Malik - amanxcvii
```