package PhilosophersDinner;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class PhilosopherMonitor {
  public static final int N = 5;
  private static final int THINKING = 2;
  private static final int HUNGRY = 1;
  private static final int EATING = 0;
  private final int[] state = new int[N];
  private final Lock lock = new ReentrantLock();
  private final Condition[] condition = new Condition[N];
  public final int times = 200; // Number of times each philosopher will eat
  private final int[] eatCount = new int[N]; // Track eat counts for each philosopher

  public PhilosopherMonitor() {
    for (int i = 0; i < N; i++) {
      state[i] = THINKING;
      condition[i] = lock.newCondition();
    }
  }

  private void test(int phnum) {
    if (state[(phnum + 1) % N] != EATING
      && state[(phnum + N - 1) % N] != EATING
      //&& state[(phnum - 1) % N] != EATING //TURN THE PROGRAM SEQUENTIAL
      && state[phnum] == HUNGRY) {
      state[phnum] = EATING;
      condition[phnum].signal();
    }
  }

  /*public void takeFork(int phnum) throws InterruptedException {
    lock.lock();
    try {
      state[phnum] = HUNGRY;
      test(phnum);
      if (state[phnum] != EATING) {
          condition[phnum].await();
      }
      System.out.println("Philosopher " + (phnum + 1) + " is Eating");
      eatCount[phnum]++;
    } finally {
      lock.unlock();
    }
  }*/
  public void takeFork(int phnum) throws InterruptedException {
    lock.lock();
    try {
        state[phnum] = HUNGRY;
        test(phnum);
        while (state[phnum] != EATING) {
            condition[phnum].await();
        }
        System.out.println("Philosopher " + (phnum + 1) + " is Eating");
        eatCount[phnum]++;
    } finally {
        lock.unlock();
    }
}

  public int getEatCount(int phnum) {
    return eatCount[phnum];
  }

  public void putFork(int phnum) {
    lock.lock();
    try {
      state[phnum] = THINKING;
      test((phnum + 1) % N);
      test((phnum + N - 1) % N);
    } finally {
        lock.unlock();
    }
  }
}
