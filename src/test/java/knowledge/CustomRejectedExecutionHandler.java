package knowledge;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author zhailz
 * @Date 2017年8月30日 - 下午4:42:44
 * @Doc: 
 */
public class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

  private static final Logger logger = LoggerFactory.getLogger(
      CustomRejectedExecutionHandler.class);

  @Override
  public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
    logger.error("线程池的状态: 阻塞队列中元素的个数:{}, \n " + "线程池中worker的个数:{} \n " + "线程池中任务的个数是:{}.", e
        .getQueue().size(), e.getActiveCount(), e.getTaskCount());

  }

}
