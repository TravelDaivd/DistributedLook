package com.david.look.solution.server;

import com.alibaba.fastjson.JSON;
import com.david.look.solution.common.RedisDistributedLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Component
public class RedisDistributedService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisDistributedLock redisDistributedLock;

    public void addData(String clientId,String data){
        if(!redisDistributedLock.existLock("up_lock_redis")){
           Map<String,String> dataMap = new HashMap<>();
           dataMap.put("requestId",clientId);
           dataMap.put("requestDate",data);
           redisDistributedLock.lockUpEX("up_lock_redis",JSON.toJSONString(dataMap),5);
        }else{
            String lockClientId = redisDistributedLock.getLockValue("up_lock_redis");
            logger.info(MessageFormat.format("加锁的请求客户端：{0}",clientId));
            logger.info(MessageFormat.format("锁住的请求客户端：{0}",lockClientId));
        }
    }
}
