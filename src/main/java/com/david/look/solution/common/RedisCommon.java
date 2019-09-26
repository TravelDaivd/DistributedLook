package com.david.look.solution.common;


import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;

import java.time.Duration;

public class RedisCommon {

    public static RedisClient getRedisClient(){
        RedisURI redisURI = new RedisURI();
        redisURI.setHost("172.162.15.111");
        redisURI.setPort(6379);
        redisURI.setPassword("redis");
        redisURI.setTimeout(Duration.ofSeconds(100));
        return RedisClient.create(redisURI);
    }

    public  static StatefulRedisConnection getStatefulConnection(){
        return getRedisClient().connect();
    }




}
