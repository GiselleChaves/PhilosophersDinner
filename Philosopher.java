package PhilosophersDinner;

class Philosopher implements Runnable {
  static final int times = 200;
  private final int id;
  private final PhilosopherMonitor monitor;

  public Philosopher(int id, PhilosopherMonitor monitor) {
    this.id = id;
    this.monitor = monitor;
  }

  @Override
  public void run() {
    try {
      int c = 0;
      while (c < monitor.times) {
        Thread.sleep(1000);
        monitor.takeFork(id);
        Thread.sleep(500);
        monitor.putFork(id);
        c++;
        System.out.println("Philosopher " + (id+1) + " finished eating " + monitor.getEatCount(id) + " times.");
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
}
}
