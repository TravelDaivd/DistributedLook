package com.david.look.solution;

public class WechatClient implements ServerInterface {

    private int amount;
    private String account;

    public WechatClient(int amount,String account) {
        this.amount = amount;
        this.account = account;
    }
    @Override
    public void sendMessage(int amount, String account) {
        System.out.println("客户:"+account+"完成账户充值，金额："+amount);
    }

    public void forRecharge(){
        System.out.println("开始调用服务器端进行充值");
        new WechatServer().recharge(amount, account, this);
    }

}
