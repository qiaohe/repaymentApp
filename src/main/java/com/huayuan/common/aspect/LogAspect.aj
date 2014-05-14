package com.huayuan.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * Created by Johnson on 5/14/14.
 */
public aspect LogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);
    private static final String LOG_MESSAGE_TEMPLATE = "Calling method {0} of {1} with Args {2}";

    @Before("execution(* com.huayuan.web.*Controller*.*(..))")
    public void loggingAdvice(JoinPoint joinPoint) {
        if (joinPoint.getTarget() == null) return;
        final String clazz = joinPoint.getTarget().getClass().getName();
        final String method = joinPoint.getSignature().getName();
        final String args = Arrays.toString(joinPoint.getArgs());
        LOGGER.debug(MessageFormat.format(LOG_MESSAGE_TEMPLATE, method, clazz, args));
    }
}
