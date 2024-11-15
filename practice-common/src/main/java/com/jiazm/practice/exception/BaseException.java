package com.jiazm.practice.exception;

import com.jiazm.practice.enums.ApiMessageBaseEnum;
import com.jiazm.practice.model.Message;
import lombok.Data;
import org.slf4j.helpers.MessageFormatter;

/**
 * 业务异常基类
 *
 * @author lilong10
 * @date 2019/03/19 16:41
 * copyright 2019 www.lenovo.com
 */
@Data
public class BaseException extends RuntimeException {

    private String code;
    private String msg;
    private Object[] msgArgs;

    public BaseException(String msg) {
        super(msg);
        this.code = Message.ERROR_DEFAULT_CODE;
        this.msg = msg;
    }

    public BaseException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(String code, String msg, Throwable t) {
        super(msg, t);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(ApiMessageBaseEnum api) {
        super(api.getMessage());
        this.code = api.getCode();
        this.msg = api.getMessage();
    }

    /**
     * 追加参数
     *
     * @param msgArgs
     * @return
     */
    public BaseException addArgs(Object... msgArgs) {
        this.setMsgArgs(msgArgs);
        return this;
    }

    /**
     * 返回消息，并追加参数
     *
     * @return
     */
    @Override
    public String getMessage() {
        if (msgArgs != null && msgArgs.length > 0) {
            return MessageFormatter.arrayFormat(msg, msgArgs).getMessage();
        }
        return super.getMessage();
    }

}
