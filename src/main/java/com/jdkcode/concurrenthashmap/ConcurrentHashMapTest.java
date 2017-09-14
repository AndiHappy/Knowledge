package com.jdkcode.concurrenthashmap;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhailz
 * @Date 2017年8月28日 - 上午9:52:09
 * @Doc: 
 */
public class ConcurrentHashMapTest {
  
  public static void main(String[] args) {
    System.out.println("concurrentHashMap");
    ConcurrentHashMap<String, Object> hashmap = new ConcurrentHashMap<String, Object>(16);
    System.out.println ();
    new Thread(new Ha(hashmap)).start();
    new Thread(new Ha(hashmap)).start();
    new Thread(new Ha(hashmap)).start();
  }

  static class Ha implements Runnable{
    ConcurrentHashMap<String, Object> hashmap;
    public Ha(ConcurrentHashMap<String, Object> hashmap) {
      this.hashmap = hashmap;
    }
    @Override
    public void run() {
      this.hashmap.put(Thread.currentThread().getName(), this);
      try {
        Thread.sleep(100000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    
  }
}
