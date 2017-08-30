package knowledge;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread state for a thread which has not yet started.
 */
//NEW, 这个状态的实例直接的新建一个线程就是

/**
 * Thread state for a runnable thread.  A thread in the runnable
 * state is executing in the Java virtual machine but it may
 * be waiting for other resources from the operating system
 * such as processor.
 */
//RUNNABLE, 线程的就绪和运行，合并称之为RUNNABLE

/**
 * Thread state for a thread blocked waiting for a monitor lock.
 * A thread in the blocked state is waiting for a monitor lock
 * to enter a synchronized block/method or
 * reenter a synchronized block/method after calling
 * {@link Object#wait() Object.wait}.
 */
//BLOCKED,阻塞状态，等待锁的状态 synchronized，

/**
 * Thread state for a waiting thread.
 * A thread is in the waiting state due to calling one of the
 * following methods:
 * <ul>
 *   <li>{@link Object#wait() Object.wait} with no timeout</li>
 *   <li>{@link #join() Thread.join} with no timeout</li>
 *   <li>{@link LockSupport#park() LockSupport.park}</li>
 * </ul>
 *
 * <p>A thread in the waiting state is waiting for another thread to
 * perform a particular action.
 *
 * For example, a thread that has called <tt>Object.wait()</tt>
 * on an object is waiting for another thread to call
 * <tt>Object.notify()</tt> or <tt>Object.notifyAll()</tt> on
 * that object. A thread that has called <tt>Thread.join()</tt>
 * is waiting for a specified thread to terminate.
 */
//WAITING, 等待状态，强调的是等待另外的一个线程的动作

/**
 * Thread state for a waiting thread with a specified waiting time.
 * A thread is in the timed waiting state due to calling one of
 * the following methods with a specified positive waiting time:
 * <ul>
 *   <li>{@link #sleep Thread.sleep}</li>
 *   <li>{@link Object#wait(long) Object.wait} with timeout</li>
 *   <li>{@link #join(long) Thread.join} with timeout</li>
 *   <li>{@link LockSupport#parkNanos LockSupport.parkNanos}</li>
 *   <li>{@link LockSupport#parkUntil LockSupport.parkUntil}</li>
 * </ul>
 */
//TIMED_WAITING, 有时间的等待，和 wait 区分开来的原因

/**
 * Thread state for a terminated thread.
 * The thread has completed execution.
 */
//TERMINATED; 停止的状态
public class ThreadState {

    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(new TimeWaiting(), "TimeWaitingThread").start();
        new Thread(new Waiting(), "WaitingThread").start();
        new Thread(new Blocked(), "BlockedThread-1").start();
        new Thread(new Blocked(), "BlockedThread-2").start();
        new Thread(new Sync(), "SyncThread-1").start();
        new Thread(new Sync(), "SyncThread-2").start();
        
    }

    /**
     * Thread.State.TIMED_WAITING
     * */

//    "TimeWaitingThread" #13 prio=5 os_prio=31 tid=0x00007fcb2281c800 nid=0x5d03 waiting on condition [0x0000700001961000]
//    java.lang.Thread.State: TIMED_WAITING (sleeping)
//    at java.lang.Thread.sleep(Native Method)
//    at java.lang.Thread.sleep(Thread.java:340)
//    at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
//    at com.SleepUtils.second(SleepUtils.java:11)
//    at com.ThreadState$TimeWaiting.run(ThreadState.java:90)
//    at java.lang.Thread.run(Thread.java:745)
   
    static class TimeWaiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                SleepUtils.second(1000);
            }
        }
    }

//
//    "WaitingThread" #14 prio=5 os_prio=31 tid=0x00007fcb2188d000 nid=0x5f03 in Object.wait() [0x0000700001a64000]
//  java.lang.Thread.State: WAITING (on object monitor)
//  at java.lang.Object.wait(Native Method)
//      - waiting on <0x000000076abf5fc0> (a java.lang.Class for com.ThreadState$Waiting)
//  at java.lang.Object.wait(Object.java:502)
//  at com.ThreadState$Waiting.run(ThreadState.java:101)
//      - locked <0x000000076abf5fc0> (a java.lang.Class for com.ThreadState$Waiting)
//  at java.lang.Thread.run(Thread.java:745)

  /**
   * reenter a synchronized block/method after calling  Object.wait}
   * */
    static class Waiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

//block状态：没有拿到锁的
//
//    "BlockedThread-2" #16 prio=5 os_prio=31 tid=0x00007fcb25051000 nid=0x6303 waiting for monitor entry [0x0000700001c6a000]
//  java.lang.Thread.State: BLOCKED (on object monitor)
//  at com.ThreadState$Blocked.run(ThreadState.java:114)
//      - waiting to lock <0x000000076abf9e08> (a java.lang.Class for com.ThreadState$Blocked)
//  at java.lang.Thread.run(Thread.java:745)
//
//  第一个线程占用Block锁之后，然后就一直的等待，导致第二个拿不到锁，就是Block的状态
//      "BlockedThread-1" #15 prio=5 os_prio=31 tid=0x00007fcb22020800 nid=0x6103 waiting on condition [0x0000700001b67000]
//  java.lang.Thread.State: TIMED_WAITING (sleeping)
//  at java.lang.Thread.sleep(Native Method)
//  at java.lang.Thread.sleep(Thread.java:340)
//  at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
//  at com.SleepUtils.second(SleepUtils.java:11)
//  at com.ThreadState$Blocked.run(ThreadState.java:114)
//      - locked <0x000000076abf9e08> (a java.lang.Class for com.ThreadState$Blocked)
//  at java.lang.Thread.run(Thread.java:745)
    static class Blocked implements Runnable {
        public void run() {
            synchronized (Blocked.class) {
                while (true) {
                    SleepUtils.second(100);
                }
            }
        }
    }

    //等待，但不是block
//    "SyncThread-2" #18 prio=5 os_prio=31 tid=0x00007fcb21053800 nid=0x6703 waiting on condition [0x0000700001e70000]
//  java.lang.Thread.State: WAITING (parking)
//  at sun.misc.Unsafe.park(Native Method)
//      - parking to wait for  <0x000000076abee000> (a java.util.concurrent.locks.ReentrantLock$NonfairSync)
//  at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
//  at java.util.concurrent.locks.AbstractQueuedSynchronizer.parkAndCheckInterrupt(AbstractQueuedSynchronizer.java:836)
//  at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireQueued(AbstractQueuedSynchronizer.java:870)
//  at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(AbstractQueuedSynchronizer.java:1199)
//  at java.util.concurrent.locks.ReentrantLock$NonfairSync.lock(ReentrantLock.java:209)
//  at java.util.concurrent.locks.ReentrantLock.lock(ReentrantLock.java:285)
//  at com.ThreadState$Sync.run(ThreadState.java:124)
//  at java.lang.Thread.run(Thread.java:745)


//    "SyncThread-1" 等待可以理解
//      "SyncThread-1" #17 prio=5 os_prio=31 tid=0x00007fcb22021800 nid=0x6503 waiting on condition [0x0000700001d6d000]
//  java.lang.Thread.State: TIMED_WAITING (sleeping)
//  at java.lang.Thread.sleep(Native Method)
//  at java.lang.Thread.sleep(Thread.java:340)
//  at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
//  at com.SleepUtils.second(SleepUtils.java:11)
//  at com.ThreadState$Sync.run(ThreadState.java:126)
//  at java.lang.Thread.run(Thread.java:745)

  static class Sync implements Runnable {

        @Override
        public void run() {
            lock.lock();
            try {
                SleepUtils.second(100000);
            } finally {
              lock.unlock();
            }

        }

    }
}