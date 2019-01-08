/*
 * Copyright (c) 2017. All rights reserved by Taimi Robot.
 * Created by yaocui on 17-9-11 下午12:54.
 */

package com.tmirob.medical.commonmodule.aspect;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * Web层日志切面
 */
@Aspect
@Order(1)
@Component
public abstract class WebLogAspect {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Long startTime = System.currentTimeMillis();

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        ObjectMapper mapper = new ObjectMapper();
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg != null && !(arg instanceof BeanPropertyBindingResult)
                    && !(arg instanceof PageRequest)
                    && !MultipartFile.class.isAssignableFrom(arg.getClass())
                    && !HttpServletResponse.class.isAssignableFrom(arg.getClass())) {
                logger.info("ARGS: " + mapper.writeValueAsString(arg));
            }
        }

        //执行
        Object result = joinPoint.proceed();
        ObjectMapper om = new ObjectMapper();

        // 处理完请求，返回内容
        logger.info("RESPONSE : " + om.writeValueAsString(result));
        logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime));
        return result;
    }

    @AfterThrowing(throwing = "ex", pointcut = "execution(* com.tmirob.medical..*(..))")
    public void doAfterThrowing(JoinPoint joinPoint, Exception ex) throws Throwable {
        Signature signature = joinPoint.getSignature();
        logger.error("Caught exception in method: " + signature.getName() + " with arguments: "
                + Arrays.toString(joinPoint.getArgs()));
        logger.error("Full String is: " + signature.toString());
        logger.error("Exception is: " + Arrays.toString(ex.getStackTrace()));
    }
}