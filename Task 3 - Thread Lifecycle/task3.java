// 3. Thread Lifecycle
// Problem: Create a program that demonstrates the lifecycle of a thread from creation to termination.
// Task: Write a Java program that creates a thread, starts it, puts it to sleep for a few seconds, and then terminates it. Print the state of the thread at each stage.
public class task3 {
    @SuppressWarnings("static-access")
    public static void main(String [] args) throws InterruptedException{
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run(){
            while(true){
            System.out.print("Thread is running \n");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            }
        }
    });
    System.out.print(thread.getState() + "\n");
    thread.start();
    System.out.print(thread.getState() + "\n");
    thread.sleep(5000);
    // Thread.sleep(5000);
    System.out.print(thread.getState() + "\n");

}
}
