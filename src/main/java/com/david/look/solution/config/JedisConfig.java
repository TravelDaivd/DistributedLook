package com.david.look.solution.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import redis.clients.jedis.commands.JedisCommands;

@Configuration
public class RedisConfig {

    @Bean("JedisCommands")
    public JedisCommands  jedisCommands(RedisConnection redisConnection){
        JedisCommands jedisCommands = (JedisCommands)redisConnection.getNativeConnection();
        return jedisCommands;
    }

}

