package com.david.look.solution.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
       // ExecutorService executor = Executors.newCachedThreadPool();
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
        // 初次延迟1秒执行，间隔3秒执行

        List<Task> tasks = new ArrayList<Task>();
        for (int i = 0; i < 3; i++) {
            tasks.add(new Task(String.valueOf(i)));
        }
        try {
            List<Future<Result>> futures = executor.invokeAll(tasks);
            executor.shutdown();
            System.out.printf("Main: Start print resutls\n");
            for (Future<Result> future : futures) {
                System.out.printf("%s : %s\n", future.get().getName(), future.get().getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.printf("Main: End of programe.\n");
    }
}
