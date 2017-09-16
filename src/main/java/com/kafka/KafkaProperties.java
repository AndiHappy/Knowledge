package com.kafka;

/**
 * @author zhailz
 * @date 17/9/15 - 下午2:29.
 */
public class KafkaProperties {
  //topic的名称
  public static final String TOPIC = "topic1";
  //
  public static final String KAFKA_SERVER_URL = "localhost";
  //默认的端口号
  public static final int KAFKA_SERVER_PORT = 9092;
  public static final int KAFKA_PRODUCER_BUFFER_SIZE = 64 * 1024;
  public static final int CONNECTION_TIMEOUT = 100000;
  public static final String TOPIC2 = "topic2";
  public static final String TOPIC3 = "topic3";
  public static final String CLIENT_ID = "SimpleConsumerDemoClient";
  //消费者组名称
  public static final String CONSUMER1 = "consumer1";
  public static final String CONSUMER2 = "consumer2";

  private KafkaProperties() {}
}
