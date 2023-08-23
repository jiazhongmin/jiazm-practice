package com.jiazm.practice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author jiazm3
 * @date 2021/12/13 11:25
 */
@Configuration
public class GlobalCorsConfig {
    /**
     * 允许访问的域白名单, 可以配置到配置中心
     */
    @Value("${cors.allow.ips}")
    String allowIps;

    @Bean
    public CorsFilter corsFilter() {
        var corsConfig = new CorsConfiguration();
        String[] allowDomains = allowIps.split(",");
        corsConfig.setAllowedOrigins(Arrays.asList(allowDomains));

        //是否发送Cookie信息
        corsConfig.setAllowCredentials(true);

        //允许的请求方式, 可以配置到配置中心
        List<String> allowedMethods = new ArrayList<>();
        allowedMethods.add("OPTIONS");
        allowedMethods.add("HEAD");
        allowedMethods.add("GET");
        allowedMethods.add("PUT");
        allowedMethods.add("POST");
        allowedMethods.add("DELETE");
        allowedMethods.add("PATCH");
        corsConfig.setAllowedMethods(allowedMethods);
        corsConfig.addAllowedOrigin("*");
        //允许的头信息
        corsConfig.addAllowedHeader("*");

        //添加映射路径，/**: 拦截请求
        var configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", corsConfig);

        return new CorsFilter(configSource);
    }

}
