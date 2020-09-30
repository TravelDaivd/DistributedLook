package com.david.look.solution;

public class AddThread  implements Runnable {

    int sum=0;
    @Override
    synchronized public void run() {
        System.out.println(Thread.currentThread().getName()+":"+System.currentTimeMillis()/1000);
        // TODO Auto-generated method stub
        for(int i=1;i<=1000;i++)
        {
            sum++;
        }
    }
}
