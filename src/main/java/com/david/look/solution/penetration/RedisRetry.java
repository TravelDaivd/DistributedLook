package com.david.look.solution.penetration;


import java.util.concurrent.Callable;

public interface RedisRetry extends Callable<String> {

    void handleRetry(String retryData,int count,RedisLock redisLock);


    void retryFailAfter(String retryData);

    void retrySuccessAfter(String retryData);


}
