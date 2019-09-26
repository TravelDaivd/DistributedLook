package com.david.look.solution;

import com.david.look.solution.config.RunThread;
import com.david.look.solution.server.RedisDistributedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DistributedLookApplication/* implements CommandLineRunner*/ {

    public static void main(String[] args) {
        SpringApplication.run(DistributedLookApplication.class, args);
    }
    @Autowired
    private RedisDistributedService redisDistributedService;

    /*@Override
    public void run(String... args) {
        ScheduledExecutorService ses= Executors.newScheduledThreadPool(10);
        RunThread runThread=new RunThread(redisDistributedService);
        ses.scheduleAtFixedRate(runThread, 0, 2, TimeUnit.SECONDS);  //没有延迟，2s执行一次任务
        ses.scheduleAtFixedRate(runThread, 1, 2, TimeUnit.SECONDS);   //1s后开始执行任务，2s执行一次
    }*/

}
