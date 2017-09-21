package com.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zhailz
 * @date 17/9/18 - 上午10:37.
 */
public class RedisClient {

  private static int MAX_ACTIVE = 512;
  private static int MAX_IDLE = 256;
  private static int MAX_WAIT = 5120;
  private static int TIME_OUT = 3000;
  private static boolean TEST_ON_BORROW = false;
  private static boolean TEST_WHILE_IDLE = false;

  private static final Logger logger = LoggerFactory.getLogger (RedisClient.class);
  /**
   * 公用redis集群 共享资源池
   */
  private static ShardedJedisPool shardCommonPool = null;

  private static JedisPool jedisPool = null;

  static  {
    List<JedisShardInfo> listJedisShardInfo = new ArrayList<> ();
    String serverString = "127.0.0.1:6379";
    logger.info("path={},serverString={}", serverString, serverString);
    String serverArray[] = serverString.split("[,]");
    JedisShardInfo jsi;
    String redisInfo[];
    for (String server : serverArray) {
      redisInfo = server.split("[:]");
      jsi = new JedisShardInfo(redisInfo[0], Integer.parseInt(redisInfo[1].toString()), TIME_OUT);
      if (redisInfo.length > 2) {// 需要密码访问
        jsi.setPassword(redisInfo[2]);
      }
      listJedisShardInfo.add(jsi);
    }
    JedisPoolConfig config = new JedisPoolConfig();
    config.setMaxTotal(MAX_ACTIVE);
    config.setMaxIdle(MAX_IDLE);
    config.setMaxWaitMillis(MAX_WAIT);
    config.setTestOnBorrow(TEST_ON_BORROW);
    config.setTestWhileIdle(TEST_WHILE_IDLE);
    shardCommonPool = new ShardedJedisPool (config, listJedisShardInfo);
//    jedisPool = new JedisPool ();
    logger.info("init  " + serverString + " shardedpool  ok");
  }

  public static ShardedJedis getJedis(){
    return shardCommonPool.getResource ();
  }

  public static void main(String[] args){
    ShardedJedis jedis = getJedis ();
    String key = "testkey";

    jedis.set (key,"testvalue");

    String  value = jedis.get (key);
    String value1 = jedis.echo (key);
    System.out.println ("echo key : "+ value1);
    System.out.println (value);
    //从1开始
    jedis.setrange (key,2,"range");
//    jedis.setrange (key,1000000000,"range");
//    System.out.println (jedis.get (key));
//    Exception in thread "main" redis.clients.jedis.exceptions.JedisDataException: ERR string exceeds maximum allowed size (512MB)

    jedis.setbit (key,3,"1");
    System.out.println (jedis.get (key));

    // list命令

    String listkey = "listkey";
    if(jedis.exists (listkey)){
      jedis.del (listkey);
    }
    jedis.lpush (listkey,"1","2","3","4","5","6");
    jedis.rpush (listkey,"8","9","10");
    int i = 0;
    System.out.println ("长度：" + jedis.llen (listkey));
    System.out.println ("o索引值：" + jedis.lindex (listkey, i));
    System.out.println ("左弹出："+ jedis.lpop (listkey));
    System.out.println ("右弹出：" + jedis.rpop (listkey));
    System.out.println ("长度：" + jedis.llen (listkey));

    jedis.lpushx (listkey,"1");
    System.out.println ("长度：" + jedis.llen (listkey));

    //hashmap的数据
    String hashname = "hash";
    String hashKey = "hashKey";
    jedis.hset(hashname, hashKey, "hashvalue");
    String hv = jedis.hget(hashname, hashKey);
    System.out.println(hv);
//    
//    jedis.hdel(hashKey, fields)
//    jedis.hexists(hashKey, field)
//    jedis.hlen(key)
//    jedis.hincrBy(hashKey, field, value);
      Set<String> keys = jedis.hkeys(hashname);
      System.out.println(keys);
    
    Jedis jedisonly = jedis.getShard(hashKey);
    jedisonly.multi();
    
    // 订阅和发布
    // redis中消息不会持久化，当订阅者不在线时，消息就会丢失。(没有处理离线消息的功能)

  }
}
