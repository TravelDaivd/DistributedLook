package com.david.look.solution.penetration;

public interface RedisLock {

    void mutexLock(String requestData,int retry_count);

    void lockSuccessAfter(String lockData);

    void lockFailAfter(String lockData,String message);
}
