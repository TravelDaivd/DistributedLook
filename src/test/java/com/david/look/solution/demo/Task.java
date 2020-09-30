package com.david.look.solution.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Task implements Callable<Result> {
    private String name;

    public Task(String name) {
        this.name = name;
    }



    @Override
    public Result call() throws Exception {
        long duration = (long) (Math.random() * 10);
        System.out.printf("%s: Starting and Waiting %d seconds for results.\n", this.name, duration);
        TimeUnit.SECONDS.sleep(duration);
        int value = 0;
        for (int i = 0; i < 5; i++) {
            value += (int) (Math.random() * 100);
        }
        return new Result(this.name, value);
    }
}
