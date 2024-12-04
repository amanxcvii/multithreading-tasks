// 9. Fork/Join Framework
// Problem: Use the Fork/Join framework to calculate the sum of an array of integers.
// Task: Write a Java program using ForkJoinPool and RecursiveTask to divide the array into smaller parts and sum them in parallel.

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class task9 {

    private static final int THRESHOLD = 10;

    static class sumTask extends RecursiveTask<Integer> {
        int[] array;
        int start;
        int end;

        sumTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start > THRESHOLD) {
                int mid = (start + end) / 2;
                sumTask leftTask = new sumTask(array, start, mid);
                sumTask rightTask = new sumTask(array, mid, end);
                leftTask.fork();
                int rightResult = rightTask.compute();
                int leftResult = leftTask.join();
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
            array[i] = i + 1;
        }
        ForkJoinPool pool = new ForkJoinPool();
        sumTask task = new sumTask(array, 0, array.length);
        int result = pool.invoke(task);
        System.out.println("Sum: " + result);
    }
}