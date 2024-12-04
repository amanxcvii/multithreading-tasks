### Understanding ForkJoinPool and RecursiveTask in Java

### Brief Explanation of ForkJoinPool

**ForkJoinPool** is a framework in Java designed for parallel execution of tasks using a **divide-and-conquer** approach. It efficiently splits large tasks into smaller subtasks, executes them concurrently, and then combines their results.

### Key Concepts

1. **Forking**: A task is split into smaller subtasks using the `fork()` method, which submits the task to the pool for parallel execution.
   
2. **Joining**: The `join()` method waits for a previously forked task to complete and retrieves its result.

3. **Work-Stealing**: Idle threads "steal" unfinished tasks from busy threads, ensuring maximum CPU utilization and reducing idle time.

4. **RecursiveTask**: Used when tasks return results (e.g., summing an array).
   
5. **RecursiveAction**: Used when tasks perform actions but don't return results (e.g., sorting).

### Example Scenario
Consider calculating the sum of an array:
- **Divide**: Split the array into smaller segments.
- **Conquer**: Sum each segment in parallel.
- **Combine**: Merge the results to get the final sum.

### Benefits
- Efficient parallelism by utilizing available CPU cores.
- Automatic task distribution and load balancing using work-stealing.

### Simple Use Case
A ForkJoinPool with default threads (based on CPU cores) handles the parallel execution, improving performance for large, recursive tasks.

### Types of Tasks in ForkJoinPool

1. **RecursiveTask**:
   - Used when the task **returns a result**.
   - Example: Summing an array of numbers.
   
   ```java
   class SumTask extends RecursiveTask<Integer> {
       @Override
       protected Integer compute() {
           // Logic to compute and return a result
       }
   }
   ```

2. **RecursiveAction**:
   - Used when the task **does not return a result**.
   - Example: Sorting an array or updating shared data.
   
   ```java
   class SortTask extends RecursiveAction {
       @Override
       protected void compute() {
           // Logic to perform an action without returning a value
       }
   }
   ```

---

## Program: Sum of Array Using ForkJoinPool

### Code Example
```java
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Task9 {

    private static final int THRESHOLD = 10;

    static class SumTask extends RecursiveTask<Integer> {
        int[] array;
        int start, end;

        SumTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start > THRESHOLD) {
                int mid = (start + end) / 2;
                SumTask leftTask = new SumTask(array, start, mid);
                SumTask rightTask = new SumTask(array, mid, end);

                leftTask.fork(); // Asynchronously fork the left task
                int rightResult = rightTask.compute(); // Synchronously compute right task
                int leftResult = leftTask.join(); // Wait for the left task's result

                return leftResult + rightResult;
            } else {
                int sum = 0;
                for (int i = start; i < end; i++) {
                    sum += array[i];
                }
                return sum;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = new int[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1; // Fill array with numbers from 1 to 100
        }

        ForkJoinPool pool = new ForkJoinPool(); // Create ForkJoinPool
        SumTask task = new SumTask(array, 0, array.length);

        int result = pool.invoke(task); // Execute the task and get the result
        System.out.println("Sum: " + result); // Output: Sum: 5050
    }
}
```

## How the Program Works

1. **Array Division**: 
   - The array is recursively split into smaller subarrays until the size is less than or equal to `THRESHOLD` (10).
   
2. **Parallel Execution**:
   - The left subtask is forked to execute asynchronously (`leftTask.fork()`).
   - The right subtask is computed immediately (`rightTask.compute()`).
   
3. **Result Combination**:
   - `leftTask.join()` ensures the left task's result is available before combining it with the right result.
   - The results from the subtasks are summed and returned.

4. **Sequential Computation**:
   - If the subarray size is below `THRESHOLD`, the sum is calculated sequentially using a `for` loop.

---

## Modifications and Experimentation

1. **Change the Threshold**:
   - Modify `THRESHOLD` to control the size of subtasks:
     ```java
     private static final int THRESHOLD = 20; // Larger threshold for larger subtasks
     ```

2. **Custom Thread Count**:
   - Specify the number of threads in the ForkJoinPool:
     ```java
     ForkJoinPool pool = new ForkJoinPool(4); // Limit to 4 threads
     ```

3. **Experiment with Larger Arrays**:
   - Increase the array size to observe parallel processing:
     ```java
     int[] array = new int[1_000_000]; // Sum of 1 million numbers
     ```

---

## Running the Program

### Compile the Program:
```bash
javac Task9.java
```

### Run the Program:
```bash
java Task9
```

### Expected Output:
```
Sum: 5050
```

---

## Conclusion
- **ForkJoinPool** is ideal for tasks that can be recursively divided into smaller subtasks.
- The **work-stealing mechanism** ensures efficient CPU utilization, making it perfect for parallel computation on multi-core processors.
- Understanding ForkJoinPool provides insight into concurrent programming in Java, enabling developers to write efficient, parallelized applications.

--- 

## Questions and Answers

### 1. **Will `pool.invoke(task)` run on the main thread or ForkJoinPool threads?**
   - `pool.invoke(task)` blocks the main thread until the task completes.
   - The actual `compute()` method is executed on ForkJoinPool threads, not the main thread.

### 2. **Does `.fork()` create additional threads?**
   - No, `.fork()` does not create new threads. It queues the task in the ForkJoinPool for parallel execution.
   - The pool uses existing threads or idle threads through work-stealing.

### 3. **How many threads does ForkJoinPool have by default?**
   - By default, it creates **`Runtime.getRuntime().availableProcessors()`** threads, equal to the number of CPU cores.

### 4. **What happens if you specify more threads than available CPU cores?**
   - ForkJoinPool can handle more tasks than cores, but performance may not improve due to **context switching overhead**.
   - Extra threads may not increase CPU utilization effectively for CPU-bound tasks.

### 5. **How does `fork()` and `join()` work together?**
   - **`fork()`**: Queues the subtask for parallel execution in the ForkJoinPool.
   - **`join()`**: Waits for the result of the forked task.
   - This ensures tasks are executed in parallel but results are combined synchronously.

### 6. **Are ForkJoinPool threads different from ThreadPoolExecutor threads?**
   - Yes, ForkJoinPool threads focus on **recursive**, **divide-and-conquer** tasks and support **work-stealing**.
   - **ThreadPoolExecutor** is used for general task execution without the recursive divide-and-conquer model.

---

### Author
```
Author: Aman Malik - amanxcvii
```