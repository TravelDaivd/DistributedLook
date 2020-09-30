package com.david.look.solution.demo.server;

import com.alibaba.fastjson.JSON;
import com.david.look.solution.penetration.AbstractRedisMutex;
import com.david.look.solution.redisLock.RedisDistributedLock;
import com.david.look.solution.redisLock.RedisException;
import com.david.look.solution.config.RedisBloomFilter;
import com.google.common.hash.BloomFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Component
public class DemoService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisDistributedLock redisDistributedLock;

    @Autowired
    private RedisBloomFilter redisBloomFilter;
    @Autowired
    private AbstractRedisMutex redisMutex;

    public void addData(String clientId,String data){
        Map<String,String> dataMap = new HashMap<>();
        dataMap.put("requestId",clientId);
        dataMap.put("requestDate",data);
        try {
            boolean bool = redisDistributedLock.lockUpEX("up_lock_redis", JSON.toJSONString(dataMap), 5);
            if(!bool){
                String lockClientId = redisDistributedLock.getLockValue("up_lock_redis");
                logger.info(MessageFormat.format("加锁的请求客户端：{0}",clientId));
                logger.info(MessageFormat.format("锁住的请求客户端：{0}",lockClientId));
            }
        } catch (RedisException e) {
            e.printStackTrace();
        }
    }


    public void redisMutex(String clientId,String data){
        redisMutex.mutexLock(data,10);
    }
    public boolean filterData(String param){
        String name = JSON.parseObject(param).getString("name");
        BloomFilter bloomFilter = redisBloomFilter.getBloomFilter();
        return redisBloomFilter.isExistFormBloomFile(bloomFilter,name);
    }
}
