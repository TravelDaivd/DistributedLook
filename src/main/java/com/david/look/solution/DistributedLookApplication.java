package com.david.look.solution;

import com.david.look.solution.config.RedisBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 有实现代码
 * 1、解决缓存雪崩 -> 缓存同一时间大面积的失效，这个时候又来了一波请求，结果请求都怼到数据库上，从而导致数据库连接异常
 *      方案（中奖的感觉）：采用分布式锁,为每个请求数据都加上过期时间，等到释放锁后再重新选择请求加锁，
 *      缺点：访问的请求数据没有进行有效的保存，没有获取锁的数据将会丢弃
 * 2、解决缓存穿透 ->即黑客故意去请求缓存中不存在的数据，导致所有的请求都怼到数据库上，从而数据库连接异常
 *      方案：1>第一道防线设置过滤器，把可能引用穿透的数据放到布隆过滤器中，所有的请求先从这里筛选一遍，布隆过滤器有一定失败率
 *           2>第二道防线采用分布式锁，把要请求的数据线进行锁住，然后进行数据库操作，没有锁住的数据先休眠一段时间进行重试，
 *
 */




@SpringBootApplication
public class DistributedLookApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DistributedLookApplication.class, args);
    }
 /*   @Autowired
    private DemoService demoService;

    @Autowired
    private AbstractRedisMutex redisMutex;
*/

    @Autowired
    private RedisBloomFilter redisBloomFilter;

    @Override
    public void run(String... args) {
        String writerName = "李浩,王虎,胡总,刘辉,马虎,刘海,刘宁";
        redisBloomFilter.addDataToBloomFilter(writerName.split(","));

       /* ScheduledExecutorService ses= Executors.newScheduledThreadPool(10);
        RunThread runThread=new RunThread(demoService,redisMutex);
        ses.scheduleAtFixedRate(runThread, 0, 2, TimeUnit.SECONDS);  //没有延迟，2s执行一次任务
        ses.scheduleAtFixedRate(runThread, 1, 2, TimeUnit.SECONDS);   //1s后开始执行任务，2s执行一次*/
    }

}
