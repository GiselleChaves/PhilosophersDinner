package PhilosophersDinner;

public class DiningPhilosophers {

  public static void main(String[] args) throws InterruptedException {

    PhilosopherMonitor monitor = new PhilosopherMonitor();
    Thread[] threads = new Thread[PhilosopherMonitor.N];

    // Create and start philosopher threads
    for (int i = 0; i < PhilosopherMonitor.N; i++) {
      Philosopher philosopher = new Philosopher(i, monitor);
      threads[i] = new Thread(philosopher);
      threads[i].start();
      System.out.println("Philosopher " + (i + 1) + " is thinking...");
    }

    // Wait for all philosopher threads to finish
    for (Thread thread : threads) {
      thread.join();
    }

    // Display the number of times each philosopher finished eating
    for (int i = 0; i < PhilosopherMonitor.N; i++) {
      System.out.println("Philosopher " + i + " finished eating " + monitor.getEatCount(i) + " times.");
    }
  }
}
