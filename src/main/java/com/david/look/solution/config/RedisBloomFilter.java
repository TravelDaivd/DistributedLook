package com.david.look.solution.config;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class RedisBloomFilter {

    Logger logger = LoggerFactory.getLogger(getClass());

    private BloomFilter bloomFilter ;

    public  void addDataToBloomFilter(String...array){
        List<String> filterDataArray = Arrays.asList(array);
        this.initBloomFilter(filterDataArray.size(),0.01);
        for(String data : filterDataArray){
            bloomFilter.put(data);
        }
    }

    public void initBloomFilter(long dataCount,double fpp){
        this.setBloomFilter(BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8),dataCount,fpp));
    }

    public  void addDataToBloomFilter(String data,int dataCount){
        if(bloomFilter == null ){
            this.initBloomFilter(dataCount,0.01);
        }
        bloomFilter.put(data);
    }

    public synchronized boolean isExistFormBloomFile(BloomFilter bloomFilter,String data){
        if(bloomFilter.mightContain(data)){
            logger.info(MessageFormat.format("在布隆过滤器存在的数据：{0}",data));
            return true;
        }
        return false;
    }

    public synchronized  ConcurrentLinkedDeque<String> isExistFormBloomFile (BloomFilter bloomFilter,String...array){
        ConcurrentLinkedDeque<String> filterFailData = new ConcurrentLinkedDeque<>();
        for(String filterData:array){
            if(bloomFilter.mightContain(filterData)){
                filterFailData.add(filterData);
            }
        }
        return filterFailData;
    }

    public BloomFilter getBloomFilter() {
        return bloomFilter;
    }

    public void setBloomFilter(BloomFilter bloomFilter) {
        this.bloomFilter = bloomFilter;
    }
}
