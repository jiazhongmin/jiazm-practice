package com.jiazm.practice.spring;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * Utils - Spring
 *
 * @version 3.0
 */
@Component
public final class SpringUtils implements ApplicationContextAware, DisposableBean {

    /**
     * applicationContext
     */
    private static ApplicationContext applicationContext;

    /**
     * 不可实例化
     */
    private SpringUtils() {
    }

    /**
     * 获取applicationContext
     *
     * @return applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtils.applicationContext = applicationContext;
    }

    /**
     * 获取实例
     *
     * @param name Bean名称
     * @return 实例
     */
    public static Object getBean(String name) {
        Assert.hasText(name,"");
        return applicationContext.getBean(name);
    }

    /**
     * 获取实例
     *
     * @param name Bean名称
     * @param type Bean类型
     * @return 实例
     */
    public static <T> T getBean(String name, Class<T> type) {
        Assert.hasText(name,"");
        Assert.notNull(type,"");
        return applicationContext.getBean(name, type);
    }

    /**
     * 获取实例
     *
     * @param requiredType Bean类型
     * @return
     */
    public static <T> T getBean(Class<T> requiredType) {
        Assert.notNull(requiredType,"");
        return applicationContext.getBean(requiredType);
    }

    /**
     * 获取实例Map
     *
     * @param requiredType Bean类型
     * @return 实例
     */
    public static <T> Map<String, T> getBeanMap(Class<T> requiredType) {
        Assert.notNull(requiredType,"");
        return applicationContext.getBeansOfType(requiredType);
    }

    @Override
    public void destroy() throws Exception {
        applicationContext = null;
    }
}