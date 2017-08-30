package knowledge;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WaitNotify {
     boolean flag = true;
     Object  lock = new Object();
     
     public void testNotifyAndWait() throws InterruptedException {
       Thread waitThread = new Thread(new Wait(), "WaitThread");
       waitThread.start();
       TimeUnit.SECONDS.sleep(1);

       Thread notifyThread = new Thread(new Notify(), "NotifyThread");
       notifyThread.start();
     }

    public static void main(String[] args) throws Exception {
      WaitNotify test = new WaitNotify();
      test.testNotifyAndWait();
    }

     class Wait implements Runnable {
        public void run() {
            synchronized (lock) {
                while (flag) {
                    try {
                        System.out.println(Thread.currentThread() + " flag is true. wait @ "
                                           + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        lock.wait();
                        System.out.println(Thread.currentThread() + "  lock wait 执行结束 @ "
                            + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                    } catch (InterruptedException e) {
                    }
                }
                System.out.println(Thread.currentThread() + " flag is false. running @ "
                                   + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }

     class Notify implements Runnable {
        public void run() {
            synchronized (lock) {
                System.out.println(Thread.currentThread() + " hold lock. notify @ " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notifyAll();
                System.out.println(Thread.currentThread() + " lock.notifyAll 执行 " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                SleepUtils.second(5);
                flag = false;
                SleepUtils.second(5);
                System.out.println(Thread.currentThread() + " flag = false 执行 " + new SimpleDateFormat("HH:mm:ss").format(new Date()));

            }
//            SleepUtils.second(5);
            
            synchronized (lock) {
                System.out.println(Thread.currentThread() + " hold lock again. sleep @ "
                                   + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                SleepUtils.second(5);
                System.out.println(Thread.currentThread() + " hold lock again. and sleep over again @ "
                    + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }
}
