package com.david.look.solution.demo.web;


import com.david.look.solution.demo.server.RedisData;
import com.david.look.solution.demo.server.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.concurrent.Callable;


@RestController
public class DemoController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DemoService demoService;

    @PostMapping("/add/data")
    public String addData(@RequestBody RedisData redisData){
        demoService.addData(redisData.getClientId(),redisData.getRequestData());
        return "success";
    }



    @PostMapping("/filter/mutex/data")
    public String redisMutex(@RequestBody RedisData redisData){
        demoService.redisMutex(redisData.getClientId(),redisData.getRequestData());
        return "success";
    }

    @PostMapping("/filter/data")
    public String bloomFilter(@RequestBody String param){
        return String.valueOf(demoService.filterData(param));
    }





    @GetMapping("/asny/test")
    public Callable<String> asnyTest(){
        logger.info(MessageFormat.format("主线程开始：{0}",Thread.currentThread().getName()));
        Callable<String> result = () -> {
            logger.info(MessageFormat.format("副线程开始：{0}",Thread.currentThread().getName()));
            Thread.sleep(1000);
            logger.info("副线程返回");
            return null;
        };
        logger.info("主线程返回");
        return result;
    }

}
