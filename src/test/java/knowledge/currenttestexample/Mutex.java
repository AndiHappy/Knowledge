package knowledge.currenttestexample;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 10-2
 */
public class Mutex implements Lock {
    private static class Sync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = -4387327721959839431L;
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }
        public boolean tryAcquire(int acquires) {
            assert acquires == 1; // Otherwise unused
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        protected boolean tryRelease(int releases) {
            assert releases == 1; // Otherwise unused
            if (getState() == 0)
                throw new IllegalMonitorStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        Condition newCondition() {
            return new ConditionObject();
        }
    }

    private final Sync sync = new Sync();

    public void lock() {
        sync.acquire(1);
    }
    @Override
    public void unlock() {
    this.sync.release(1);
    }
    
    public void release() {
      sync.release(1);
  }

    public boolean tryLock() {
        return sync.tryAcquire(1);
    }


    public Condition newCondition() {
        return sync.newCondition();
    }

    public boolean isLocked() {
        return sync.isHeldExclusively();
    }

    public boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }

    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
    }
    
    public static void main(String[] args) throws Exception {
      Mutex lock = new Mutex();
      new Thread(new TimeWaiting(lock),"1").start();
      new Thread(new TimeWaiting(lock),"2").start();
      new Thread(new TimeWaiting(lock),"3").start();
      new Thread(new TimeWaiting(lock),"4").start();
    }
    
    static class TimeWaiting implements Runnable {
      Mutex lock;
      public TimeWaiting(Mutex lock) {
        this.lock = lock;
      }
      @Override
      public void run() {
        this.lock.lock();
          while (true) {
              SleepUtils.second(1000);
          }
      }
  }


}
