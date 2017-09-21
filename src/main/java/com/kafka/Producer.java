package com.kafka;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Producer extends Thread {
    protected final KafkaProducer<Integer, String> producer;
    protected final String topic;
    protected final Boolean isAsync;

    public Producer(String topic, Boolean isAsync) {
        Properties props = new Properties();
        //kafka 服务所在的地址
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "DemoProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
//        props.put (ProducerConfig.PARTITIONER_CLASS_CONFIG,PartitionClass.class.getName ());
        producer = new KafkaProducer<>(props);
        this.topic = topic;
        this.isAsync = isAsync;
    }

    public void run() {
        int messageNo = 1;
        while (true) {
            String messageStr = this.topic+"_Message_" + messageNo;
            long startTime = System.currentTimeMillis();
            ProducerRecord record = new ProducerRecord<>(topic, messageNo, messageStr);
            if (isAsync) { // Send asynchronously
                DemoCallBack callBack = new DemoCallBack(startTime, messageNo, messageStr);
                producer.send(record, callBack);
            } else { // Send synchronously
                try {
                    Future afterSend = producer.send(record);
                    System.out.println("Sent message: (" + messageNo + ", " + messageStr + ")");
                    Object res = afterSend.get();
                    System.out.println ("发送的结果是："+String.valueOf (res));
                    Thread.sleep (10000);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            ++messageNo;

        }
    }

    public static void main(String[] args) {
        Producer producer = new Producer (KafkaProperties.TOPIC2, false);
        ProducerRecord<Integer, String> recoder = new ProducerRecord<Integer, String>
            (producer.topic, 129240,"message_123");
        while(true) {
          producer.producer.send (recoder);
          recoder = new ProducerRecord<Integer, String>
              (producer.topic, 129241,"message_1234");
          producer.producer.send (recoder);
        }
    }
}

class DemoCallBack implements Callback {

    private final long startTime;
    private final int key;
    private final String message;

    public DemoCallBack(long startTime, int key, String message) {
        this.startTime = startTime;
        this.key = key;
        this.message = message;
    }

    /**
     * A callback method the user can implement to provide asynchronous handling of request completion. This method will
     * be called when the record sent to the server has been acknowledged. Exactly one of the arguments will be
     * non-null.
     *
     * @param metadata  The metadata for the record that was sent (i.e. the partition and offset). Null if an error
     *                  occurred.
     * @param exception The exception thrown during processing of this record. Null if no error occurred.
     */
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        if (metadata != null) {
            System.out.println(
                "message(" + key + ", " + message + ") sent to partition(" + metadata.partition() +
                    "), " +
                    "offset(" + metadata.offset() + ") in " + elapsedTime + " ms" + " keySize: "+
            metadata.serializedKeySize ()+" valueSize: "+ metadata.serializedValueSize ());

        } else {
            exception.printStackTrace();
        }
    }
}