package com.david.look.solution.penetration;

import com.david.look.solution.common.RedisConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.concurrent.*;


public class AbstractRedisRetry implements RedisRetry {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private RedisLock redisLock;
    private String retryData;
    private int retryCount;

    @Override
    public void handleRetry(String retryData, int retryCount,RedisLock redisLock) {
        this.retryData = retryData;
        this.retryCount = retryCount;
        setRedisLock(redisLock);
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
        // 初次延迟1秒执行，间隔3秒执行
        executorService.scheduleAtFixedRate(this::call,1,3, TimeUnit.SECONDS);
    }


    @Override
    public void retryFailAfter(String retryData) {
        RedisConstant.redisMutexMap.remove(retryData);
    }

    @Override
    public void retrySuccessAfter(String retryData) {
        RedisConstant.redisMutexMap.remove(retryData);
    }

    public void setRedisLock(RedisLock redisLock) {
        this.redisLock = redisLock;
    }


    @Override
    public String call() {
        return this.retry(retryData,retryCount);
    }

    private String retry(String retryData, int retry_count){
        if(!RedisConstant.redisMutexMap.containsKey(retryData)){
            RedisConstant.redisMutexMap.put(retryData,1);
            redisLock.mutexLock(retryData,retry_count);
        }else{
            Integer count = RedisConstant.redisMutexMap.get(retryData);
            logger.info(MessageFormat.format("重试的数据：{0}；已经重试的次数：{1}",retryData,count));
            if(count==retry_count){
                this.retryFailAfter(retryData);
                logger.info(MessageFormat.format("重试失败将要丢弃的数据：{0}",retryData));
                return  "fail";
            }else{
                RedisConstant.redisMutexMap.put(retryData,count+1);
                redisLock.mutexLock(retryData,retry_count);
                logger.info(MessageFormat.format("数据：{0}；下次重试次数:{1}",retryData,count+1));
            }
        }

        return "success";
    }
}
