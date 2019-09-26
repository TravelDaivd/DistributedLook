package com.david.look.solution;

import com.david.look.solution.common.RedisDistributedLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistributedLookApplicationTests {
    @Autowired
    private RedisDistributedLock redisDistributedLock;

    @Test
    public void lockUpTest() {
        String key = "lock";
        String value = "互斥性/不会发生死锁/具有容错性/解铃还须系铃人";
        long time = 1600;
        System.out.println( redisDistributedLock.lockUpEX(key, value, time));
    }

    @Test
    public void getLockTest() {
        String key = "lock";
        System.out.println( redisDistributedLock.getLockValue(key));
    }

    @Test
    public void getUnLockTest() {
        String key = "lock";
        String value = "互斥性/不会发生死锁/具有容错性/解铃还须系铃人";
        System.out.println( redisDistributedLock.unLock(key,value));
    }
    @Test
    public void lockExistTest(){
        String key = "lock";
        System.out.println( redisDistributedLock.existLock(key));
    }

}
