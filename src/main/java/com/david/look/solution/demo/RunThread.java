package com.david.look.solution.demo;

import com.david.look.solution.demo.server.DemoService;
import com.david.look.solution.penetration.AbstractRedisMutex;
import org.springframework.stereotype.Component;

@Component
public class RunThread implements Runnable{

    private DemoService redisDistributedService;
    private AbstractRedisMutex redisMutex;
    public RunThread(DemoService redisDistributedService, AbstractRedisMutex redisMutex){
       this.redisDistributedService = redisDistributedService;
       this.redisMutex = redisMutex;
    }

    @Override
    synchronized public void run() {
        String clientId = Thread.currentThread().getName();
        String requestData = "redis data ："+clientId;
        //redisMutex.mutexLock(requestData,10);
        redisDistributedService.addData(clientId,requestData);

    }
}
