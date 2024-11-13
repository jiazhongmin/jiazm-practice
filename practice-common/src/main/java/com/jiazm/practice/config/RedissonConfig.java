//package com.jiazm.practice.config;
//
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author jiazhongmin
// * @Date 2023/9/26 18:34
// **/
//@Configuration
//public class RedissonConfig {
//    @Value("${spring.redis.host}")
//    private String host;
//    @Value("${spring.redis.port}")
//    private String port;
//    @Value("${spring.redis.password}")
//    private String password;
//    @Bean
//    public RedissonClient redissonClient() {
//        Config config = new Config();
//        // 配置 Redisson 客户端连接信息
//        config.useSingleServer()
//                .setAddress("redis://"+host+":"+6379)
//                .setPassword(password); // 如果有密码的话
//
//        return Redisson.create(config);
//    }
//}
