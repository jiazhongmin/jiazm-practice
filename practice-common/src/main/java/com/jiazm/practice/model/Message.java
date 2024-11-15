package com.jiazm.practice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通用消息返回
 *
 * @author LiLong
 * @date 2014年11月21日 下午3:18:13
 */
@JsonInclude(Include.NON_NULL)
@Data
@NoArgsConstructor
public class Message<T> implements Serializable {

    public static final String SUCCESS_DEFAULT_CODE = "0";
    public static final String SUCCESS_DEFAULT_MSG = "ok";
    public static final String ERROR_DEFAULT_CODE = "-1";
    public static final String ERROR_DEFAULT_MSG = "error";

    private static final long serialVersionUID = 7289310002935043203L;

    /**
     * 0为正常返回，>0为业务错误,<0为系统错误
     */
    private String code;

    /**
     * 提示msg
     */
    private String msg;

    /**
     * 内容
     */
    private T result;

    /**
     * 参数
     */
    @JsonIgnore
    private Object[] msgArgs;

    public Message(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Message(String code, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    /**
     * 成功消息
     *
     * @return
     */
    public static Message success() {
        return new Message(SUCCESS_DEFAULT_CODE, SUCCESS_DEFAULT_MSG);
    }

    /**
     * 成功消息
     *
     * @param result
     * @return
     */
    public static Message success(Object result) {
        return new Message(SUCCESS_DEFAULT_CODE, SUCCESS_DEFAULT_MSG, result);
    }

    /**
     * 成功消息
     *
     * @param msg
     * @param result
     * @return
     */
    public static Message success(String msg, Object result) {
        return new Message(SUCCESS_DEFAULT_CODE, msg, result);
    }

    /**
     * 失败消息
     *
     * @return
     */
    public static Message error() {
        return new Message(ERROR_DEFAULT_CODE, ERROR_DEFAULT_MSG);
    }

    /**
     * 失败消息
     *
     * @param result
     * @return
     */
    public static Message error(Object result) {
        return new Message(ERROR_DEFAULT_CODE, ERROR_DEFAULT_MSG, result);
    }

    /**
     * 失败消息
     *
     * @param msg
     * @return
     */
    public static Message error(String msg) {
        return new Message(ERROR_DEFAULT_CODE, msg);
    }

    /**
     * 失败消息
     *
     * @param code
     * @param msg
     * @return
     */
    public static Message error(String code, String msg) {
        return new Message(code, msg);
    }

    /**
     * 追加参数
     *
     * @param msgArgs
     * @return
     */
    public Message addArgs(Object... msgArgs) {
        this.setMsgArgs(msgArgs);
        return this;
    }

    /**
     * 是否成功
     */
    @JsonIgnore
    public boolean isSuccess() {
        return SUCCESS_DEFAULT_CODE.equals(code);
    }

}