package knowledge.currenttestexample;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhailz
 * @Date 2017年8月30日 - 下午4:26:33
 * @Doc:
 */
public class ThreadPool {
  static ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 10, 6L, 
      TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(10), 
      new CustomRejectedExecutionHandler());
  
  static ThreadPoolExecutor executor2 = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, 
      TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
  
  public static void main(String[] args) throws Exception {
    System.out.println(Runtime.getRuntime().availableProcessors());
    for (int i = 0; i < 10; i++) {
      executor2.submit(new Domino("cachethread"+i));
    }
    
    for (int i = 0; i < 10; i++) {
      executor.submit(new Domino("thread"+i));
    }
   
  }
  
  static class Domino extends Thread {
    public Domino(String string) {
     super(string);
    }

    @Override
    public void run() {
      try {
        Thread.sleep(10000000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    
  }
}
