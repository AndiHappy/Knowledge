package knowledge;

import java.util.concurrent.locks.Lock;

import knowledge.ThreadState.Blocked;
import knowledge.ThreadState.Sync;
import knowledge.ThreadState.TimeWaiting;
import knowledge.ThreadState.Waiting;

/**
 * 10-11
 */
public class TwinsLockTest {
  final Lock lock = new TwinsLock();

  public void test() {
    for (int i = 0; i < 10; i++) {
      Worker w = new Worker();
      w.setName("thread-" + i);
      w.start();
    }

  }

  class Worker extends Thread {
    public void run() {
        System.out.println(Thread.currentThread().getName() + "begine ");
        lock.lock();
        try {
          SleepUtils.second(1);
          System.out.println(Thread.currentThread().getName());
          SleepUtils.second(1);
        } finally {
          lock.unlock();
          System.out.println(Thread.currentThread().getName() + "end ");
        }
    }
  }

  public static void main(String[] args) {
    TwinsLockTest test = new TwinsLockTest();
    test.test();
  }
}
