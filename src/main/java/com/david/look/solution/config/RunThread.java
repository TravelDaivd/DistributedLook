package com.david.look.solution.config;

import com.david.look.solution.server.RedisDistributedService;
import org.springframework.stereotype.Component;

@Component
public class RunThread implements Runnable{

    private  RedisDistributedService redisDistributedService;
    public RunThread(RedisDistributedService redisDistributedService){
       this.redisDistributedService = redisDistributedService;
    }

    @Override
    synchronized public void run() {
        String clientId = Thread.currentThread().getName();
        String requestData = "互斥性/不会发生死锁/具有容错性/解铃还须系铃人";
        redisDistributedService.addData(clientId,requestData);

    }
}
