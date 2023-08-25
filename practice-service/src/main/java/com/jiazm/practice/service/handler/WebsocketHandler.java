package com.jiazm.practice.service.handler;

import com.alibaba.fastjson.JSON;
import com.jiazm.practice.entity.WebsocketMessage;
import com.jiazm.practice.service.UserLogService;
import com.jiazm.practice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * // 接口路径 ws://127.0.0.1:8082/websocket/jiazhongmin;
 *
 * @author jiazhongmin
 * @Date 2023/8/24 15:30
 **/
@Component
@Slf4j
@ServerEndpoint("/websocket/{itCode}")
public class WebsocketHandler {
    private static UserService userService;
    private static UserLogService userLogService;
    private Session session;
    //记录日志
    @Resource
    public void setUserService(UserService userService) {
        WebsocketHandler.userService = userService;
    }

    @Resource
    public void setUserLogService(UserLogService userLogService) {
        WebsocketHandler.userLogService = userLogService;
    }

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象
    private static CopyOnWriteArraySet<WebsocketHandler> websocketUtils = new CopyOnWriteArraySet<>();
    // 用来存在线连接数
    private static Map<String, Session> sessionPool = new HashMap<>();

    /**
     * 链接成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("itCode") String itCode) {
        try {
            this.session = session;
            websocketUtils.add(this);
            sessionPool.put(itCode, session);
            sendOneMessage(itCode, "连接成功");
            log.info("【websocket消息】有新的连接，总数为:" + websocketUtils.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        try {
            websocketUtils.remove(this);
            log.info("【websocket消息】连接断开，总数为:" + websocketUtils.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     * @param
     */
    @OnMessage
    public void onMessage(@PathParam("itCode") String itCode, String message) {

        log.info("【websocket消息】收到客户端消息:" + message);
            WebsocketMessage toMessage = JSON.parseObject(message, WebsocketMessage.class);
            //根据message中的to属性获取接收消息的用户的session，利用其session将消息转发过去
            Session toSession = sessionPool.get(toMessage.getTo());
            // 将消息转发给to的人
            try {
                toSession.getBasicRemote().sendText(toMessage.getMsg());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    /**
     * 发送错误时的处理
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误,原因:" + error.getMessage());
        error.printStackTrace();
    }


    /**
     * 推消息给前端
     *
     * @param userId
     * @param message
     * @return
     */
    private void sendOneMessage(String userId, String message) {
        Session session = sessionPool.get(userId);
        if (session != null && session.isOpen()) {
            try {
                log.info("【推给前端消息】 :" + message);
                //高并发下，防止session占用期间，被其他线程调用
                synchronized (session) {
                    session.getBasicRemote().sendText(message);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

