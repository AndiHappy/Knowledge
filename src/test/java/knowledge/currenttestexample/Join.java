package knowledge.currenttestexample;

import java.util.concurrent.TimeUnit;

/**
 * 6-13
 */
public class Join {
    public static void main(String[] args) throws Exception {
        Thread previous = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Domino(previous,String.valueOf(i)), String.valueOf(i));
            thread.start();
            previous = thread;
        }

        TimeUnit.SECONDS.sleep(3);
        System.out.println(Thread.currentThread().getName() + " terminate.");
    }

    static class Domino implements Runnable {
        private Thread thread;
        private String name;
        public Domino(Thread thread,String name) {
            this.thread = thread;
            this.name = name;
        }

        public void run() {
          System.out.println(this.name +" begine");
            try {
                System.out.println(thread.getName() +" join().");
                thread.join();
                System.out.println(thread.getName() +" join() over");
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread().getName() + " terminate." + this.name +"  value");
        }
    }
}