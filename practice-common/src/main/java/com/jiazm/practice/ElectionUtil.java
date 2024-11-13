package com.jiazm.practice;

import com.alibaba.fastjson.JSONObject;

/**
 * @author jiazhongmin
 * @Date 2024/11/6 15:51
 **/
public class ElectionUtil {
    public static void main(String[] args) {
        try {
            String response = HttpUtils.httpGetToken("https://shankapi.ifeng.com/feedflow/usavote2024/info", null);
            JSONObject jsonObject = JSONObject.parseObject(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
