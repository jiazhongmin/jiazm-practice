package com.jiazm.practice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author ：dongjian2
 * @date ：2020/11/26
 * @description：xxs攻击过滤器
 * @modified By：dongjian
 */
@WebFilter(filterName = "xssFilter", urlPatterns = "/*")
@Slf4j
@Configuration
public class XssRequestFilter implements Filter {

    private static final String WHITE_LIST = "getMenu";

    @Override
    public void init(FilterConfig filterConfig) {
        // Do nothing because of
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest hsr = (HttpServletRequest) request;
            //涉及保存操作的进行xss过滤
            if (!hsr.getRequestURL().toString().contains(WHITE_LIST)) {
                request = new XssHttpServletRequestWrapper((HttpServletRequest) request);
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Do nothing because of
    }
}
