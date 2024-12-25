package com.jiazm.practice.aop;

import com.google.common.collect.Lists;
import com.jiazm.practice.JsonUtils;
import com.jiazm.practice.exception.BaseException;
import com.jiazm.practice.response.GeneralResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;


/**
 * aop异常处理,token验证
 *
 * @author jiazm3
 * @date 2021-12-27 17:31:19
 */
@Aspect
@Component
@Slf4j
public class CommonAspect {
    public CommonAspect() {
    }

    @Resource
    private HttpServletResponse response;
    private static final String ERROR_PARAMS = "参数异常: {} in {}.{}()";
    private static final String IMPORT_SYSTEM_INFO_EXCEL = "importSystemInfoExcel";
    private static final String IMPORT_TABLE_DESCRIPTION_EXCEL = "importTableDescriptionExcel";
    private static final String IMPORT_TABLE_FIELD_EXCEL = "importTableFieldExcel";
    private static final String IMPORT_MISMATCHED_EXCEL = "misMatchedUploadFile";
    private static final String IMPORT_MATCHED_EXCEL = "matchedUploadFile";
    private static final String IMPORT_COMMENTS_EXCEL = "dataFocalCommentsUploadFile";
    private static final String UPLOAD_FILE = "uploadFile";
    private static final String UPLOAD = "upload";
    private static final String UPLOAD_CONDITION = "uploadCondition";
    private static final String SYSTEM_ERROR = "There is system error, please contact administrator";
    private static final String TOKEN_INVALIDATION = "Your login has expired, please refresh the interface";

    /**
     * aop开关
     */
    private Boolean aopEnable;

    @Value("${aop.enable}")
    public void setAopEnable(String aopEnable) {
        this.aopEnable = Boolean.parseBoolean(aopEnable);
    }

    private PathMatcher pathMarch = new AntPathMatcher();
    @Value("${white.list}")
    private String whiteList;
    private final List<String> whites = Lists.newArrayList();

    @PostConstruct
    public void init() {
        if (StringUtils.isNotBlank(this.whiteList)) {
            whites.addAll(Arrays.asList(whiteList.split(",")));
        }
    }

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Do nothing because of
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(com.jiazm.practice.controller.*)")
    public void applicationPackagePointcut() {
        // Do nothing because of
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("异常: {}.{}() 原因 = '{}' 信息 = '{}'", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL", e.getMessage(), e);
    }


    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (log.isDebugEnabled()) {
            if (IMPORT_SYSTEM_INFO_EXCEL.equals(joinPoint.getSignature().getName()) || IMPORT_TABLE_DESCRIPTION_EXCEL.equals(joinPoint.getSignature().getName())
                    || IMPORT_TABLE_FIELD_EXCEL.equals(joinPoint.getSignature().getName()) || UPLOAD_FILE.equals(joinPoint.getSignature().getName())
                    || UPLOAD_CONDITION.equals(joinPoint.getSignature().getName()) || IMPORT_MISMATCHED_EXCEL.equals(joinPoint.getSignature().getName())
                    || IMPORT_MATCHED_EXCEL.equals(joinPoint.getSignature().getName())
                    || IMPORT_COMMENTS_EXCEL.equals(joinPoint.getSignature().getName()) || UPLOAD.equals(joinPoint.getSignature().getName()) || joinPoint.getSignature().getName().contains(UPLOAD)) {
                log.debug("开始: {}.{}() 参数 = {}", joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(), "excel");
            } else {
                log.debug("开始: {}.{}() 参数 = {}", joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(), paramStr(joinPoint.getArgs()));
            }
        }
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 0) {
            servletPath = servletPath + pathInfo;
        }

        if (StringUtils.isEmpty(servletPath)) {
            servletPath = request.getRequestURI();
        }
        try {
//            User user = UserUtils.getUserInfo();
//            // 验证白名单或者拦截开启状态
//            if (validWhite(servletPath) && aopEnable) {
//                if (Objects.isNull(user) || StringUtils.isBlank(user.getUsfToken())) {
//                    response.setStatus(HTTP_TOKEN_CODE);
//                    return LdmpResponse.error(TOKEN_INVALIDATION);
//                }
////                //再去验证蜻蜓token是否失效
////                try {
////                    USFResponse<UserInfoResultVo> usfRes = dmmsUsfFeignService.getUserByToken(user.getUsfToken());
////                    if (Objects.isNull(usfRes)) {
////                        return LdmpResponse.error(SYSTEM_ERROR);
////                    }
////                } catch (Exception e) {
////                    return LdmpResponse.error(SYSTEM_ERROR);
////                }
//
//            }

            //重复操作处理
//            if (validClickWhite(servletPath) && validWhite(servletPath)) {
//                String key = SHA256Utils.SHA256((user.getUsfToken() + "$" + servletPath + "$" + paramStr(joinPoint.getArgs())).getBytes());
//                boolean ownerCheck = RedisUtil.lock(key, 3L);
//                if (!ownerCheck) {
//                    throw new BaseException("正在执行,请勿重复操作");
//                }
//            }
            Object result = joinPoint.proceed();
            log.info("调用完成:{}", servletPath);
            return result;
        } catch (BaseException e) {
            log.error(ERROR_PARAMS, paramStr(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), e);
            return GeneralResponse.error(StringUtils.isNotBlank(e.getMsg()) ? e.getMsg() : SYSTEM_ERROR);
        } catch (Exception e) {
            log.error(ERROR_PARAMS, paramStr(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), e);
            return GeneralResponse.error(SYSTEM_ERROR);
        }
    }


    private String paramStr(Object[] args) {
        List<Object> list = Lists.newArrayList();
        for (Object arg : args) {
            if (arg instanceof ServletRequest || arg instanceof ServletResponse) {
                continue;
            }
            list.add(arg);
        }
        return JsonUtils.toJson(list);
    }

    private boolean validWhite(String url) {
        for (String match : whites) {
            if (pathMarch.match(match, url)) {
                return false;
            }
        }
        return true;
    }

}

