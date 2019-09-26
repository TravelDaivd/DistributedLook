package com.david.look.solution.config;

import com.david.look.solution.common.RedisCommon;
import io.lettuce.core.RedisAsyncCommandsImpl;
import io.lettuce.core.codec.StringCodec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    @Bean("RedisAsyncCommands")
    public static RedisAsyncCommandsImpl getRedisAsyncCommands(){
        return new RedisAsyncCommandsImpl(RedisCommon.getStatefulConnection(), StringCodec.UTF8);
    }

}
