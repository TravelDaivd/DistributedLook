package com.david.look.solution;

public class WechatServer {

    public void recharge(int amount,String account,ServerInterface wechatClient){
        System.out.println("服务器端开始进行充值操作");
        try {
            Thread.currentThread().sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("充值操作完成");
        //充值操作完成之后需要调用客户端的回调函数通知客户端
        wechatClient.sendMessage(amount, account);
    }
}
