package com.book.bookservice.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.book.bookservice.controller.*.*(..))")
    public void logBefore() {
        logger.info("Method called");
    }

    @Around("execution(* com.book.bookservice.controller.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Method {} called with parameters: {}", joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));
        Object result = joinPoint.proceed();
        return result;
    }

}