package com.wyh.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @author lvcyy
 * @Package com.wyh.jedis
 * @create 2022/7/21 16:14
 */
public class JedisDemo1 {
    public static void main(String[] args) {
        //创建Jedis
        Jedis jedis = new Jedis("192.168.198.134",6379);

        //测试
        String value= jedis.ping();
        System.out.println(value);
    }

}
