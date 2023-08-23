package com.jiazm.practice.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用返回结果统一处理
 *
 * @author jiazm3
 * @date 2021/12/29 16:47
 */
@Data
@NoArgsConstructor
public class GeneralResponse<T> implements Serializable {

    public static final Integer SUCCESS_CODE = 0;
    public static final Integer ERROR_CODE = -1;
    public static final String SUCCESS_MSG = "ok";
    public static final String ERROR_MSG = "error";
    public static final Integer SUCCESS_STATUS = 200;
    public static final Integer ERROR_STATUS = 500;

    @Serial
    private static final long serialVersionUID = 7289310002935043203L;

    /**
     * 0为正常返回，>0为业务错误,<0为系统错误
     */
    private Integer code;
    private Integer status;

    /**
     * 提示msg
     */
    private String msg;

    /**
     * 内容
     */
    private T result;


    public GeneralResponse(Integer code, Integer status, String msg) {
        this.code = code;
        this.msg = msg;
        this.status = status;
    }

    public GeneralResponse(Integer code, Integer status, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.status = status;
        this.result = result;
    }

    public static GeneralResponse<Object> success(Object result) {
        return new GeneralResponse<>(SUCCESS_CODE, SUCCESS_STATUS, SUCCESS_MSG, result);
    }

    public static GeneralResponse<Object> error(String msg) {
        return new GeneralResponse<>(ERROR_CODE, ERROR_STATUS, msg);
    }

}
