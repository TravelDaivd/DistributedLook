package com.david.look.solution.common;

import io.lettuce.core.RedisAsyncCommandsImpl;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.SetArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class RedisDistributedLock implements AsyncLockInterface {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisAsyncCommandsImpl<String,String> redisAsyncCommands;

    private RedisThing redisThing;


    @Override
    public boolean lockUpEX(String lockKey, String lockValue,long expireTime) {
        SetArgs setArgs = new SetArgs();
        setArgs.nx();     //仅在键不存在时设置键。
        setArgs.ex(expireTime); //设置指定的到期时间(以秒为单位)
        RedisFuture<String> redisFuture = redisAsyncCommands.set(lockKey, lockValue, setArgs);
        try{
            if(redisFuture.get().equals("OK")){
                redisThing.doThings();
                return true;
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            logger.error((MessageFormat.format("加锁失败：key：{0};value：{1};错误信息：{2}",lockKey,lockValue,e.getMessage())));
            return false;
        }
    }


    @Override
    public boolean unLock(String key, String value) {
        List<String> keys = new ArrayList<>();
        keys.add(key);
        List<String> args = new ArrayList<>();
        args.add(value);
        String[] keyArray = {key};
        RedisFuture<Long> redisFuture = redisAsyncCommands.eval(AsyncLockInterface.setLuaScript(),
                ScriptOutputType.INTEGER, keyArray, value);
        try{
            Long result = redisFuture.get();
            return result != null && result > 0;
        }catch (Exception e){
            e.printStackTrace();
            logger.error((MessageFormat.format("解锁失败：key：{0};value：{1};错误信息：{2}",key,value,e.getMessage())));
        }
        return false;
    }

    @Override
    public boolean existLock(String key) {
        RedisFuture<Long> redisFuture = redisAsyncCommands.exists(key);
        try{
            Long result = redisFuture.get();
            return  result != null && result > 0;
        }catch (Exception e){
            e.printStackTrace();
            logger.error((MessageFormat.format("查看是否存在失败：key：{0};错误信息：{1}",key,e.getMessage())));
            return false;
        }
    }


    @Override
    public String getLockValue(String key) {
        RedisFuture<String> redisFuture = redisAsyncCommands.get(key);
        try{
            String value = redisFuture.get();
            return  value;
        }catch (Exception e){
            e.printStackTrace();
            logger.error((MessageFormat.format("获取value失败：key：{0};错误信息：{1}",key,e.getMessage())));
            return null;
        }
    }

    public void setRedisThing(RedisThing redisThing) {
        this.redisThing = redisThing;
    }
}
