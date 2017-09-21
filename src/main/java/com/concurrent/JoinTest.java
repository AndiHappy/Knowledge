package com.concurrent;

class JoinTest1 implements Runnable {

  public void run() {
    try {
      System.out.println("Begin sleep");
      Thread.sleep(10);
      System.out.println("End sleep,JoinTest1执行完毕");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

class ThreadContainThread extends Thread {

  Thread thread;

  public ThreadContainThread(Thread thread) {
    this.thread = thread;
  }

  @Override
  public void run() {
    synchronized (thread) {
      System.out.println("getObjectLock");
      try {
        Thread.sleep(90000);
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
      System.out.println("ReleaseObjectLock");
    }
  }
}

public class JoinTest {
  public static void main(String[] args) {
    Thread justRun = new Thread(new JoinTest1());
    justRun.start();
    new ThreadContainThread(justRun).start();
   
    try {
      justRun.join();
      System.out.println("joinFinish");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}