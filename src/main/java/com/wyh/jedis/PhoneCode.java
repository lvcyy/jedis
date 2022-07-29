package com.wyh.jedis;

import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * @author lvcyy
 * @Package com.wyh.jedis
 * @create 2022/7/21 18:09
 */
public class PhoneCode {
    public static void main(String[] args) {
        //模拟验证码发送
        verifyCode("115645");

        getRedisCode("12331231321","123654");
    }


    //验证码的校验
    public static void getRedisCode(String phone , String code){
        //先从redis中获取到验证码

        Jedis jedis = new Jedis("192.168.198.134", 6379);

        //拼接key
        //验证码key
        String codeKey = "VerifyCode" + phone + ":code";
        String redisCode = jedis.get(codeKey);
        //判断
        if (redisCode.equals(code)){
            System.out.println("成功");
        }else {
            System.out.println("失败");
        }
        jedis.close();
    }


    // 每个手机每天只能发送三次，验证码放到redis中，设置过期时间
    public static void verifyCode( String phone){

        //连接redis
        Jedis jedis = new Jedis("192.168.198.134", 6379);

        //拼接key
        //手机发送次数key
        String countKey = "VerifyCode" + phone + ":count";
        //验证码key
        String codeKey = "VerifyCode" + phone + ":code";

        //每台手机每天只能发送三次
        String count = jedis.get(countKey);
        if (count == null){
            //没有发送次数，第一次发送
            //设置发送次数为1
            jedis.setex(countKey,24*60*60,"1");
        }else if (Integer.parseInt(count)<=2){
            //发送次数+1
            jedis.incr(countKey);
        }else if (Integer.parseInt(count)>2){
            System.out.println("今天发送次数已经超过三次了");
            return;
        }

        String vcode = getCode();
        jedis.setex(codeKey,120,vcode);
        jedis.close();
    }


    //生成六位验证码
    public static String getCode(){
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++) {
            int rand = random.nextInt(10);
            code += rand;
        }
        return code;
    }


}
