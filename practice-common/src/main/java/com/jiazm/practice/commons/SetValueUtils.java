package com.jiazm.practice.commons;


import com.jiazm.practice.exception.BaseException;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author: jiazm3
 * @date: 2022-07-22 15:37
 **/
public class SetValueUtils {
    /**
     * 创建 不同对象使用同一个方法操作相同的字段值
     *
     * @param obj    不同对象
     * @param itCode 操作人
     * @param date   时间
     */
    public static void setMethodCreateValVoid(Object obj, String itCode, Date date) {
        try {
            Method setCreateBy = obj.getClass().getMethod("setCreateBy", String.class);
            setCreateBy.invoke(obj, itCode);
            Method setCreateTime = obj.getClass().getMethod("setCreateTime", Date.class);
            setCreateTime.invoke(obj, date);
            Method setModifyBy = obj.getClass().getMethod("setModifyBy", String.class);
            setModifyBy.invoke(obj, itCode);
            Method setModifyTime = obj.getClass().getMethod("setModifyTime", Date.class);
            setModifyTime.invoke(obj, date);
            Method setIsDeleted = obj.getClass().getMethod("setIsDeleted", Integer.class);
            setIsDeleted.invoke(obj, 0);
        } catch (Exception e) {
            throw new BaseException("-999", e.getMessage());
        }
    }

    /**
     * 修改 不同对象使用同一个方法操作相同的字段值
     *
     * @param obj    不同对象
     * @param itCode 操作人
     * @param date   时间
     */
    public static void setMethodUpdateValVoid(Object obj, String itCode, Date date) {
        try {
            Method setModifyTime = obj.getClass().getMethod("setModifyTime", Date.class);
            setModifyTime.invoke(obj, date);
            Method setModifyBy = obj.getClass().getMethod("setModifyBy", String.class);
            setModifyBy.invoke(obj, itCode);
        } catch (Exception e) {
            throw new BaseException("-999", e.getMessage());
        }
    }

    /**
     * 删除不同对象使用同一个方法操作相同的字段值
     *
     * @param obj    不同对象
     * @param itCode 操作人
     * @param date   时间
     */
    public static void setMethodDelValVoid(Object obj, String itCode, Date date) {
        try {
            Method setModifyTime = obj.getClass().getMethod("setModifyTime", Date.class);
            setModifyTime.invoke(obj, date);
            Method setModifyBy = obj.getClass().getMethod("setModifyBy", String.class);
            setModifyBy.invoke(obj, itCode);
            Method setIsDeleted = obj.getClass().getMethod("setIsDeleted", Integer.class);
            setIsDeleted.invoke(obj, 1);
        } catch (Exception e) {
            throw new BaseException("-999", e.getMessage());
        }
    }
}
