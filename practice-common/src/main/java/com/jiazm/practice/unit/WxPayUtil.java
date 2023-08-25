package com.jiazm.practice.unit;

import java.util.Random;

/**
 * @author jiazhongmin
 * @Date 2023/8/25 11:12
 **/
public class WxPayUtil {
    public static String generateNonceStr(Integer length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
