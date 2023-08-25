package com.jiazm.practice.config;

import lombok.Data;

/**
 * @author jiazhongmin
 * @Date 2023/8/25 10:54
 **/
@Data
public class WxV3PayConfig {


        //平台证书序列号
        public static String mchSerialNo = "xxxxxxxxxxxxxx";

        //appID
        public static String APP_ID = "xxxxxxxxxxxxxx";

        //商户id
        public static String Mch_ID = "xxxxxxxxxxxxxx";

        // API V3密钥
        public static String apiV3Key = "xxxxxxxxxxxxxx";

        // 商户API V3私钥
        public static String privateKey = " -----BEGIN PRIVATE KEY-----" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx " +
                "-----END PRIVATE KEY-----";
}
