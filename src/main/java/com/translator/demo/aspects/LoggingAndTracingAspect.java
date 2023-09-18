package com.translator.demo.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LoggingAndTracingAspect {
    private final Logger logger = LoggerFactory.getLogger(LoggingAndTracingAspect.class);

    @Before("execution(* com.translator.demo.service.TranslatorService.*(..))")
    public void beforeServiceMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Starting method: " + methodName);
    }

    @AfterReturning(pointcut = "execution(*  com.translator.demo.service.TranslatorService.*(..))", returning = "result")
    public void afterReturningServiceMethod(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Ending method: " + methodName);
    }

    @AfterThrowing(pointcut = "execution(*  com.translator.demo.service.TranslatorService.*(..))", throwing = "exception")
    public void afterThrowingServiceMethod(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        logger.error("Exception in method " + methodName + ": " + exception.getMessage());
    }

}
