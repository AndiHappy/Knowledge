package com.kafka;

/**
 * @author zhailz
 * @date 17/9/15 - 下午3:38.
 */
public class ConsumerTwo {
  public static void main(String[] args) {
    Consumer consumerThread = new Consumer(KafkaProperties.TOPIC2, KafkaProperties.CONSUMER1);
    consumerThread.run ();
  }
}
