package com.jiazm.practice.spring;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

/**
 * Utils - Message
 *
 * @author lilong
 * @date 2019-04-01
 */
@Component
public final class MessageUtils implements ApplicationContextAware, DisposableBean {
    /**
     * applicationContext
     */
    private static ApplicationContext applicationContext;
    private static LocaleResolver localeResolver;

    /**
     * 不可实例化
     */
    private MessageUtils() {
    }

    // /**
    //  * 设置语言
    //  *
    //  * @return 国际化消息
    //  */
    // public static void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
    //     localeResolver.setLocale(request, response, locale);
    //     request.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
    // }

    // /**
    //  * 获取国际化消息
    //  *
    //  * @return 国际化消息
    //  */
    // public static String getMessage(String code, Object... args) {
    //     Assert.hasText(code);
    //     HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    //     Locale locale = localeResolver.resolveLocale(request);
    //     return applicationContext.getMessage(code, args, locale);
    // }

    // /**
    //  * 获取国际化消息
    //  *
    //  * @return 国际化消息
    //  */
    // public static String getMessage(String code, HttpServletRequest request, Object... args) {
    //     Assert.hasText(code);
    //     Locale locale = localeResolver.resolveLocale(request);
    //     return applicationContext.getMessage(code, args, locale);
    // }

    /**
     * 获取国际化消息
     *
     * @return 国际化消息
     */
    public static String getMessage(String code, Locale locale, Object... args) {
        Assert.hasText(code);
        return applicationContext.getMessage(code, args, locale);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        MessageUtils.applicationContext = applicationContext;
        MessageUtils.localeResolver = applicationContext.getBean(LocaleResolver.class);
    }

    @Override
    public void destroy() throws Exception {
        applicationContext = null;
    }
}