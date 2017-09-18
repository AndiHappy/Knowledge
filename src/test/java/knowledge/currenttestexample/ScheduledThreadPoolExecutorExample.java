package knowledge.currenttestexample;

import java.util.concurrent.*;


/**
* @author Chandan Singh
*
*/
public class ScheduledThreadPoolExecutorExample
{
   public static void main(String[] args) throws InterruptedException, ExecutionException
	{

		Runnable runnabledelayedTask = new Runnable()
		{
			@Override
			public void run()
			{
			     System.out.println(Thread.currentThread().getName()+" is Running Delayed Task");
			}
		};


		Callable callabledelayedTask = new Callable()
		{

			@Override
			public String call() throws Exception
			{
			     return  "GoodBye! See you at another invocation...";
			}
		};

		ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(4);

		scheduledPool.scheduleWithFixedDelay(runnabledelayedTask, 1, 1, TimeUnit.SECONDS);

		ScheduledFuture sf = scheduledPool.schedule(callabledelayedTask, 4, TimeUnit.SECONDS);

		String value = (String) sf.get();

		System.out.println("Callable returned"+value);

//        scheduledPool.shutdown();

		System.out.println("Is ScheduledThreadPool shutting down? "+scheduledPool.isShutdown());
		Thread.sleep(1000000);
	}
}