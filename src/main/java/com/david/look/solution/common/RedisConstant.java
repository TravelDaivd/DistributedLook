package com.david.look.solution.common;

import java.util.concurrent.ConcurrentHashMap;

public class RedisConstant {

    public static ConcurrentHashMap<String, Integer> redisMutexMap = new ConcurrentHashMap<>();
    public final static String REDIS_PENETRATION_LOCK="redis_penetration_lock";
    public final static long EXPIRE_TIME = 30;
    public static int RETRY_COUNT = 20;
}
