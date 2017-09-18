package knowledge.currenttestexample;

import java.util.concurrent.*;

public class ConcurrentTask {

  private final ConcurrentMap<Object, Future<String>> taskCache = new ConcurrentHashMap<Object, Future<String>>();

  private String executionTask(final String taskName) throws Exception {
    while (true) {
      Future<String> future = taskCache.get(taskName);
      if (future == null) {
        Callable<String> task = new Callable<String>() {
          public String call() throws InterruptedException {
            System.out.println("call():" + taskName);
            return taskName;
          }
        };
        FutureTask<String> futureTask = new FutureTask<String>(task);
        future = taskCache.putIfAbsent(taskName, futureTask); // 1.3
        if (future == null) {
          future = futureTask;
          futureTask.run();
        }
      }

      try {
        return future.get();
      } catch (CancellationException e) {
        taskCache.remove(taskName, future);
      }
    }
  }

  public static void main(String[] args) throws Exception {
    ConcurrentTask task = new ConcurrentTask();
    System.out.println(task.executionTask("test1"));
    task.executionTask("ddsd");
    Thread.sleep(100000);
  }

}
