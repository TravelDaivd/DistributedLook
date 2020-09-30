package com.david.look.solution.redisLock;

public class RedisException extends Exception {

    private Object value;

    public RedisException(){
        super();
    }

    public RedisException (String errorMessage,Object value){
        super(errorMessage);
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
