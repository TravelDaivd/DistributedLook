package com.david.look.solution;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheSerDemo {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ScheduledExecutorService ses=Executors.newScheduledThreadPool(10);
        AddThread add=new AddThread();
        ses.scheduleAtFixedRate(add, 0, 2, TimeUnit.SECONDS);  //没有延迟，2s执行一次任务
        //由时间分析可得任务1执行三次，任务2执行2次，故sum=5000
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(add.sum);
    }
}
