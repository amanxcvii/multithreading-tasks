// 4. Thread Synchronization
// Problem: Create a program that simulates a bank account where multiple threads deposit and withdraw money, ensuring thread safety.
// Task: Write a Java program using synchronized methods to manage deposits and withdrawals from a shared bank account object.

public class task4 {

    public static void main(String[] args) throws InterruptedException {
        BankAccount acc = new BankAccount(1000);
        System.out.println("Opening Balance: " + acc.getBalance() + "\n"); // Opening Balance 

        // Thread 1: Deposit 500
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000); // Simulate delay in verification and other processes
                    acc.deposit(500);
                    System.out.println("thread1 (Deposit 500): " + acc.getBalance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Thread 2: Withdraw 1300
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000); // Simulate delay in verification and other processes
                    acc.withdraw(1300);
                    System.out.println("thread2 (Withdraw 1300): " + acc.getBalance());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Thread 3: Deposit 1600
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000); // Simulate delay in verification and other processes
                    acc.deposit(1600);
                    System.out.println("thread3 (Deposit 1600): " + acc.getBalance());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        // Thread 4: Withdraw 700
        Thread thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500); // Simulate delay in verification and other processes
                    acc.withdraw(700);
                    System.out.println("thread4 (Withdraw 700): " + acc.getBalance());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Start all threads
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        // Wait for all threads to finish
        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();

        // Final balance after all operations
        System.out.println("\nFinal Balance: " + acc.getBalance());
    }
}

class BankAccount {
    private int balance;

    BankAccount(int amount) {
        this.balance = amount;
    }

    // Synchronized deposit method
    synchronized void deposit(int amount) {
        this.balance += amount;
        System.out.println("Depositing " + amount);
    }

    // Synchronized withdraw method
    synchronized void withdraw(int amount) {
        if (amount > balance) {
            System.out.println("Insufficient funds! Withdrawal of " + amount + " failed.");
        } else {
            this.balance -= amount;
            System.out.println("Withdrawing " + amount);
        }
    }

    // Get the current balance
    synchronized int getBalance() {
        return this.balance;
    }
}

/**
 *
//With Syncronised Example
Opening Balance: 1000

Insufficient funds! Withdrawal of 1300 failed.
thread2 (Withdraw 1300): 1000
Withdrawing 700
thread4 (Deposit 700): 300
Depositing 500
Depositing 1600
thread1 (Deposit 500): 2400
thread3 (Deposit 1600): 2400

Final Balance: 2400

//Without Syncronised Example

Opening Balance: 1000

Insufficient funds! Withdrawal of 700 failed.
Depositing 500
Withdrawing 1300
Depositing 1600
thread1 (Deposit 500): 1800
thread4 (Withdraw 700): 1800
thread3 (Deposit 1600): 1800
thread2 (Withdraw 1300): 1800

Final Balance: 1800

**/