# multithreading-tasks
Multithreading Tasks (Learn/Revise/Prepare)

# Java Multithreading Tasks

Welcome to the **Java Multithreading Tasks Repository**! This repository is intended to help you learn, revise, and strengthen your understanding of Java multithreading through a series of hands-on examples. Each example focuses on a specific concept in Java multithreading, from basics to advanced topics.

## Table of Contents

1. [Basics of Threads and Processes](#1-basics-of-threads-and-processes)
2. [Creating Threads](#2-creating-threads)
3. [Thread Lifecycle](#3-thread-lifecycle)
4. [Thread Synchronization](#4-thread-synchronization)
5. [Thread Communication](#5-thread-communication)
6. [Thread Safety](#6-thread-safety)
7. [Executor Framework](#7-executor-framework)
8. [Concurrent Collections](#8-concurrent-collections)
9. [Fork/Join Framework](#9-forkjoin-framework)
10. [Deadlock, Livelock, and Starvation](#10-deadlock-livelock-and-starvation)
11. [Advanced Locking Techniques](#11-advanced-locking-techniques)
12. [ThreadLocal](#12-threadlocal)
13. [Java Memory Model (JMM)](#13-java-memory-model-jmm)

---

### 1. Basics of Threads and Processes

**Problem:** Create a program that prints `"Hello from Thread X"` from multiple threads, where `X` is the thread number.

**Task:** Write a Java program that creates and starts 5 threads, each printing a unique message.

---

### 2. Creating Threads

**Problem:** Create a program that calculates the sum of integers from 1 to 100 in two different ways: by extending `Thread` and by implementing `Runnable`.

**Task:** Write two versions of the program, one using a class that extends `Thread` and another using a class that implements `Runnable`.

---

### 3. Thread Lifecycle

**Problem:** Demonstrate the lifecycle of a thread from creation to termination.

**Task:** Write a Java program that creates a thread, starts it, puts it to sleep for a few seconds, and then terminates it. Print the state of the thread at each stage.

---

### 4. Thread Synchronization

**Problem:** Simulate a bank account where multiple threads deposit and withdraw money, ensuring thread safety.

**Task:** Write a Java program using synchronized methods to manage deposits and withdrawals from a shared bank account object.

---

### 5. Thread Communication

**Problem:** Create a producer-consumer problem where one thread produces data and another thread consumes it.

**Task:** Write a Java program using `wait()` and `notify()` methods for communication between producer and consumer threads.

---

### 6. Thread Safety

**Problem:** Demonstrate the use of atomic classes to update a counter in a thread-safe manner.

**Task:** Write a Java program where multiple threads increment a shared `AtomicInteger` counter.

---

### 7. Executor Framework

**Problem:** Use the Executor framework to execute a pool of tasks that calculate the square of a number.

**Task:** Write a Java program using `ExecutorService` to submit and execute a set of tasks that compute and print the square of numbers from 1 to 10.

---

### 8. Concurrent Collections

**Problem:** Use a `ConcurrentHashMap` to count word frequencies in a list of sentences.

**Task:** Write a Java program that takes a list of sentences, splits them into words, and counts the frequency of each word using a `ConcurrentHashMap`.

---

### 9. Fork/Join Framework

**Problem:** Use the Fork/Join framework to calculate the sum of an array of integers.

**Task:** Write a Java program using `ForkJoinPool` and `RecursiveTask` to divide the array into smaller parts and sum them in parallel.

---

### 10. Deadlock, Livelock, and Starvation

**Problem:** Simulate a deadlock condition.

**Task:** Write a Java program with two threads, each trying to acquire locks on two objects in different orders, causing a deadlock. Then, modify the program to resolve the deadlock.

---

### 11. Advanced Locking Techniques

**Problem:** Use a `ReadWriteLock` to manage read and write access to a shared resource.

**Task:** Write a Java program where multiple threads read from and write to a shared data structure using `ReentrantReadWriteLock`.

---

### 12. ThreadLocal

**Problem:** Use `ThreadLocal` to maintain a unique identifier for each thread.

**Task:** Write a Java program where each thread stores its ID in a `ThreadLocal` variable and prints it.

---

### 13. Java Memory Model (JMM)

**Problem:** Demonstrate the use of the `volatile` keyword to ensure visibility of changes to a shared variable across threads.

**Task:** Write a Java program with a shared `volatile` boolean variable that one thread updates and another thread reads, demonstrating the visibility of the update.

---

## Getting Started

1. **Clone the repository**:
   git clone https://github.com/amanxcvii/multithreading-tasks
2. **Navigate into the project directory**:
   cd multithreading-tasks
3. **Compile and run each example individually, based on the directory or package structure.**
