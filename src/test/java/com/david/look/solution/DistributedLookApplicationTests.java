package com.david.look.solution;

import com.david.look.solution.redisLock.RedisDistributedLock;
import com.david.look.solution.redisLock.RedisException;
import com.david.look.solution.config.RedisBloomFilter;
import com.google.common.hash.BloomFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistributedLookApplicationTests {
    @Autowired
    private RedisDistributedLock redisDistributedLock;
    @Autowired
    private RedisBloomFilter redisBloomFilter;

    @Test
    public void lockUpTest() {
        String key = "lock";
        String value = "互斥性/不会发生死锁/具有容错性/解铃还须系铃人";
        long time = 1600;
        try {
            System.out.println( redisDistributedLock.lockUpEX(key, value, time));
        } catch (RedisException e) {
            e.printStackTrace();
        }
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

    @Test
    public void bloomFilterTest(){
        String blackName = "李浩";
        String writerName = "李浩,王虎,胡总,刘辉,马虎,刘海,刘宁";

        redisBloomFilter.addDataToBloomFilter(writerName.split(","));
        BloomFilter bloomFilter = redisBloomFilter.getBloomFilter();
        boolean bool = redisBloomFilter.isExistFormBloomFile(bloomFilter,blackName);
        System.out.println(bool);
    }


}
