package com.david.look.solution.web;


import com.david.look.solution.server.RedisData;
import com.david.look.solution.server.RedisDistributedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RedisDistributedController {

    @Autowired
    private RedisDistributedService redisDistributedService;

    @PostMapping("/add/data")
    public String addData(@RequestBody RedisData redisData){
        redisDistributedService.addData(redisData.getClientId(),redisData.getRequestData());
        return "success";
    }

}
