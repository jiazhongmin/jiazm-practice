package com.jiazm.practice.utils;

/**
 * 避免了总是写new Bean，BeanUtils.copyProperties等通用代码
 *
 * @author jiazm3
 * @date 2021/12/8 17:51
 */

import com.google.inject.internal.util.Lists;
import com.jiazm.practice.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
public class CopyClass<S, T> {

    public List<T> copyList(List<S> source, Class<T> targetClazz) {
        try {
            List<T> result = Lists.newArrayList();
            for (S s : source) {
                T tar = targetClazz.getDeclaredConstructor().newInstance();
                BeanUtils.copyProperties(s, tar);
                result.add(tar);
            }
            return result;
        } catch (Exception e) {
            log.error("复试对象属性集合出错", e);
            throw new BaseException("-9999", "服务器异常");
        }
    }

    public T copyBean(S source, Class<T> targetClazz) {
        try {
            T tar = targetClazz.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, tar);
            return tar;
        } catch (Exception e) {
            log.error("复试对象属性出错", e);
            throw new BaseException("-9999", "服务器异常");
        }
    }

    public List<T> copyListNull(List<S> source, Class<T> targetClazz) {
        if (Objects.isNull(source)) {
            return Collections.emptyList();
        }
        return copyList(source, targetClazz);
    }

    public T copyBeanNull(S source, Class<T> targetClazz) {
        if (Objects.isNull(source)) {
            return null;
        }
        return copyBean(source, targetClazz);
    }
}

