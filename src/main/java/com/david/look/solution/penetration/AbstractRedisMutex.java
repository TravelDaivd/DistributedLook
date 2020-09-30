package com.david.look.solution.penetration;

import com.david.look.solution.common.RedisConstant;
import com.david.look.solution.redisLock.RedisDistributedLock;
import com.david.look.solution.redisLock.RedisException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * 缓存穿透解决方案
 * 1、采用互斥锁
 * 缺少：异步调取: 定时重试
 */

@Component
public  class AbstractRedisMutex implements RedisLock{
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisDistributedLock distributedLock;

    private RedisRetry redisRetry;

    @Override
    public  void mutexLock(String requestData, int retry_count){
        if(distributedLock.existLock(RedisConstant.REDIS_PENETRATION_LOCK)){
            if(!distributedLock.existLock(RedisConstant.REDIS_PENETRATION_LOCK,requestData)){
                this.setRedisRetry(new AbstractRedisRetry());
                if(retry_count > 0){
                    RedisConstant.RETRY_COUNT = retry_count;
                }
                redisRetry.handleRetry(requestData,RedisConstant.RETRY_COUNT,this);
            }
        } else{
            try {
                logger.info(MessageFormat.format("加锁的数据：{0}",requestData));
                distributedLock.lockUpEX(RedisConstant.REDIS_PENETRATION_LOCK,requestData,RedisConstant.EXPIRE_TIME);
                this.lockSuccessAfter(requestData);
                //  distributedLock.unLock(RedisConstant.REDIS_PENETRATION_LOCK,requestData);

            } catch (RedisException e) {
                e.printStackTrace();
                lockFailAfter(requestData,e.getMessage());
            }
        }
    }

    /**
     * 先判断重试数据是否存在，
     * @param lockData
     */
    @Override
    public void lockSuccessAfter(String lockData) {
        if(RedisConstant.redisMutexMap.containsKey(lockData)){
            redisRetry.retrySuccessAfter(lockData);
        }
    }

    @Override
    public void lockFailAfter(String lockData,String message) {

    }


    public void setRedisRetry(RedisRetry redisRetry) {
        this.redisRetry = redisRetry;
    }
}
