package com.david.look.solution;

public class Main {

    public static void main(String[] args) {
        int amount = 100;
        String account = "123123";
        WechatClient wechatClient = new WechatClient(amount, account);
        wechatClient.forRecharge();
    }
}
