package com.book.userservice.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class CacheAspect {
    private final Map<String, Object> cache = new HashMap<>();

    @Around("execution(* com.book.userservice.controller.*.signIn(..))")
    public Object cacheResult(ProceedingJoinPoint joinPoint) throws Throwable{
        String key = generateKey(joinPoint);
        if(cache.containsKey(key)){
            return cache.get(key);
        }
        Object result = joinPoint.proceed();
        cache.put(key, result);
        return  result;
    }

    private String generateKey(JoinPoint joinPoint){
        return joinPoint.getSignature().toString() + Arrays.toString(joinPoint.getArgs());
    }
}
