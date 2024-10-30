// package Task 2 - Creating Threads;
// 2. Creating Threads
// Problem: Create a program that calculates the sum of integers from 1 to 100 in two different ways: by extending Thread and by implementing Runnable.
// Task: Write two versions of the program, one using a class that extends Thread and another using a class that implements Runnable.
public class task2 {
    public static void main(String[] args) {
        sumThread thread = new sumThread();
        sumThread1 thread1 = new sumThread1();
        Thread thread2 = new Thread(thread1);
        thread.start();
        thread2.start();
    }
}

class sumThread extends Thread {
    //Overriding the Run Method
    public void run(){
        int sum = 0;
        for (int i = 1; i <= 100; i++) {
            sum += i;
        }
        System.out.print("Sum from Extended Thread : " + sum +"\n");
    }
}

class sumThread1 implements Runnable {

    @Override
    public void run() {
        int sum = 0;
        for (int i = 1; i <= 100; i++) {
            sum += i;
        }
        System.out.print("Sum from Implemented Runnable : " + sum + "\n");
    }

}