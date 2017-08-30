package com.jdkcode.concurrentlinedqueue;

public class ConcurrentLinkedQueueTest {
  
  public static void main(String[] args) {
    System.out.println("concurrentHashMap");
    ConcurrentLinkedQueue<String> hashmap = new ConcurrentLinkedQueue<String>();
    new Thread(new Ha(hashmap)).start();
    new Thread(new Ha(hashmap)).start();
    new Thread(new Ha(hashmap)).start();
  }

  static class Ha implements Runnable{
    ConcurrentLinkedQueue<String> hashmap;
    public Ha(ConcurrentLinkedQueue<String> hashmap) {
      this.hashmap = hashmap;
    }
    @Override
    public void run() {
      this.hashmap.add(Thread.currentThread().getName());
      System.out.println(this.hashmap.first().item);

      try {
        Thread.sleep(100000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    
  }
}
