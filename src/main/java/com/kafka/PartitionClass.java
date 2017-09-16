package com.kafka;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;

/**
 * @author zhailz
 * @date 17/9/15 - 下午3:51.
 */
public class PartitionClass  implements Partitioner  {

  public PartitionClass() {
  }


  @Override public int partition (
      String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
    List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
    int numPartitions = partitions.size();
    if (key.toString ().equals ("123") && numPartitions > 0){
      return numPartitions-1;
    }else{
      return 0;
    }

  }

  @Override public void close () {

  }

  @Override public void configure (Map<String, ?> configs) {

  }
}