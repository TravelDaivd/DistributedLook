package com.david.look.solution.common;

/*异步加锁接口*/
public interface AsyncLockInterface {


    static String setLuaScript(){
        StringBuilder luaData = new StringBuilder();
        luaData.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        luaData.append("then ");
        luaData.append("    return redis.call(\"del\",KEYS[1]) ");
        luaData.append("else ");
        luaData.append("    return 0 ");
        luaData.append("end ");
        return  luaData.toString();
    }


    /**
     * 加锁 到期时间(以秒为单位)
     * @param key
     * @param obj
     * @return
     */
    boolean lockUpEX(String key, String obj,long expireTime);

    /**
     * 解锁
     * @param key
     * @param obj
     * @return
     */
    boolean unLock(String key, String obj);

    /**
     * 锁是否存在
     * @param key
     * @return
     */
    boolean existLock (String key);

    String getLockValue(String key);
}
