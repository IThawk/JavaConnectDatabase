package com.ithawk.sqldemo.dataBaseConnect;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class ConnectRedis {
    public void testRedis() {
        //0、创建池子的配置对象
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //最大闲置个数
        poolConfig.setMaxIdle(300);
        poolConfig.setMaxWaitMillis(100);
        //最小闲置个数
        poolConfig.setMinIdle(10);
        poolConfig.setMaxTotal(500);

        //1、创建一个redis的连接池
        Jedis jedis = null;
        String password = "123456";
        try (JedisPool pool = new JedisPool("172.18.1.160", 6379)) {
            //2、从池子中获取redis的连接资源
            jedis = pool.getResource();

            jedis.auth(password);


        } catch (Exception e) {
            //认证异常的时候将jedis关闭
            if (jedis != null) {
                jedis.close();
            }

        }

    }
}
