package com.david.look.solution;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 学习java 8  CompletableFuture 异步
     *https://yq.aliyun.com/articles/672802
 */

public class Shop {

    private String name ;
    public Shop(String name) {
        this.name = name;
    }
    //同步
    public Double getPrice(String product){
        return calculatePrice(product);
    }
    private double calculatePrice(String product){
        delay();
        return Math.random();
    }
    public static void delay(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public String getName() {
        return name;
    }

    private List<Shop> getShops(){
        return Arrays.asList(new Shop("A"),new Shop("B")
                ,new Shop("C"),new Shop("D")
                ,new Shop("E"),new Shop("F")
                ,new Shop("G"),new Shop("H"));

    }


    public  List<String> findPrice(String product){
        List<Shop> shops = getShops();
       // return shops.stream().map(shop -> shop.getName()+shop.getPrice(product)).collect(Collectors.toList());
        return shops.parallelStream().map(shop -> shop.getName()+shop.getPrice(product)).collect(Collectors.toList());
    }

    public  void test1(){
        long l1 = System.nanoTime();
        findPrice("huawei");
        long l2 = System.nanoTime();
        System.out.println("done = " + (l2 - l1));
    }

    public static void main(String[] args) {
        Shop shop = new Shop("1");
        System.out.println(Runtime.getRuntime().availableProcessors());
        shop.test1();
    }
}
