package knowledge;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Priority {
    private static volatile boolean notStart = true;
    private static volatile boolean notEnd   = true;

    public static void main(String[] args) throws Exception {
        List<Job> jobs = new ArrayList<Job>();
        List<Thread> jobts = new ArrayList<Thread>();
        for (int i = 0; i < 10; i++) {
            int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            Job job = new Job(priority);
            jobs.add(job);
            Thread thread = new Thread(job, "Thread:" + i);
            thread.setPriority(priority);
            jobts.add(thread);
        }
        for (Thread thread : jobts) {
          thread.start();
        }
        
        notStart = false;
        Thread.currentThread().setPriority(8);
        System.out.println("done.");
        TimeUnit.SECONDS.sleep(10);
        notEnd = false;

        for (Job job : jobs) {
            System.out.println("Job Priority : " + job.priority + ", Count : " + job.jobCount);
        }

    }

    static class Job implements Runnable {
        private int  priority;
        private long jobCount;

        public Job(int priority) {
            this.priority = priority;
        }

        public void run() {
          System.out.println("开始执行线程");
            while (notStart) {
              System.out.println("开始执行yield");
                Thread.yield();
                
            }
            System.out.println("开始执行++");
            while (notEnd) {
                Thread.yield();
                jobCount++;
            }
            
           
        }
    }
}
