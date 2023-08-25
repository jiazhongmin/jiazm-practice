package com.jiazm.practice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author jiazhongmin
 * @Date 2023/8/24 11:42
 **/
@Configuration
public class WebsocketConfig {

        /**
         * 注入ServerEndpointExporter
         * 这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
         */
        @Bean
        public ServerEndpointExporter serverEndpointExporter() {
            return new ServerEndpointExporter();
        }


}
