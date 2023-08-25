package com.jiazm.practice.entity;

import lombok.Data;

/**
 * @author jiazhongmin
 * @Date 2023/8/24 16:50
 **/
@Data
public class WebsocketMessage {
    private Integer id;
    private String from;//是谁发的
    private String to;//要发送给谁
    private String msg;//消息
    private String date;//时间
    private Integer type;//消息发送的类型，0系统群发，1用户私聊
    private Integer isRead;//消息是否已读，0未读，

}
