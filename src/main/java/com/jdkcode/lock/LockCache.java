package com.jdkcode.lock;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;


/**
 * 利用SETNX非常简单地实现分布式锁。例如：某客户端要获得一个名字foo的锁，客户端使用下面的命令进行获取： 
 * SETNX lock.foo <current Unix time + lock timeout + 1>
 * 如返回1，则该客户端获得锁，把lock.foo的键值设置为时间值表示该键已被锁定，
 * 该客户端最后可以通过DEL lock.foo来释放该锁。
 * 如返回0，表明该锁已被其他客户端取得，这时我们可以先返回或进行重试等对方完成或等待锁超时。
 * 
 */
public class LockCache {
  private static final Logger log = LoggerFactory.getLogger(LockCache.class);
  /**
   * Lock expiration in miliseconds 锁超时，防止线程在入锁以后，无限的执行等待
   */
 
  
  /**
   * 锁资源
   *
   * @param key key
   * @return true获取锁 反之没有获取锁
   * @throws InterruptedException
   */
  public boolean getLock(final String key) throws InterruptedException {
    ShardedJedis jedis = null;
    long sleepTime = 100;
    int retry = 5;
    String keySeed = "lock:" + key;
    String value = System.currentTimeMillis() + "|" + timeout;
    String[] splitString;
    String returnValue;
    long setnxResult;
    int tRetry = 0;

    try {
      jedis = getJedis();
      while (true) {
        setnxResult = jedis.setnx(keySeed, value);
        if (setnxResult == 1) {
          return true;
        } else if (setnxResult == 0) {
          returnValue = jedis.get(keySeed);
          long currentTime = System.currentTimeMillis();
          while (true) {
            if (returnValue == null) {
              break;
            }
            splitString = returnValue.split("[|]");
            if (currentTime < Long.parseLong(splitString[0]) + Long.parseLong(splitString[1])) {
              if (++tRetry > retry) return false;
              Thread.sleep(sleepTime);
              returnValue = jedis.get(keySeed);
              currentTime = System.currentTimeMillis();
//              continue;
            } else {
              value = System.currentTimeMillis() + "|" + timeout;
              String oldValue = jedis.getSet(keySeed, value);
              if (returnValue.equals(oldValue)) {
                return true;
              } else {
                break;
              }
            }
          }
        }
      }
    } catch (Exception e) {
      log.error("[LockCacheImpl]->[lock] error:[keySeed]=" + keySeed, e);
      if (jedis != null) {
        returnBrokenJedis(jedis);
      }
      return false;
    } finally {
      if (jedis != null) returnJedis(jedis);
    }
  }

  /**
   * 释放资源
   *
   */
  public void releaseLock(final String key) {
    ShardedJedis jedis = null;
    try {
      jedis = getJedis();
      String keySeed = "lock:" + key;

      if (jedis.exists(keySeed)) {
        jedis.del(keySeed);
      }
    } catch (Exception e) {
      log.error("", e);
      if (jedis != null) {
        returnBrokenJedis(jedis);
      }
    } finally {
      if (jedis != null) returnJedis(jedis);
    }

  }
  private int timeout = 500;
  private int MAX_ACTIVE = 512;
  private int MAX_IDLE = 256;
  private int MAX_WAIT = 5120;
  private int TIME_OUT = 3000;
  private boolean TEST_ON_BORROW = true;
  private boolean TEST_WHILE_IDLE = false;
  /**
   * 公用redis集群 共享资源池
   */
  private ShardedJedisPool shardCommonPool = null;


  public void initialPool(String path) {
    try {
      // 如果不为null 先清理再建立
      if (null != shardCommonPool) {
        shardCommonPool.destroy();
        shardCommonPool = null;
      }
      List<JedisShardInfo> listJedisShardInfo = new ArrayList<JedisShardInfo>();
      String serverString = "127.0.0.1:3030";
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
      shardCommonPool = new ShardedJedisPool(config, listJedisShardInfo);
    } catch (Exception e) {
     e.printStackTrace();
    }
  }
  /**
   * @param jedis
   */
  private void returnBrokenJedis(ShardedJedis jedis) {
    shardCommonPool.returnBrokenResource(jedis);
  }

  /**
   * @param jedis
   */
  private void returnJedis(ShardedJedis jedis) {
    shardCommonPool.returnResource(jedis);
  }

  /**
   * @return
   */
  private ShardedJedis getJedis() {
    return shardCommonPool.getResource();
  }
}
