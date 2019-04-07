package com.chyl.mytest.aop;

import com.chyl.mytest.ratelimiter.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Configuration
public class RateLimiterAspect {

    @Autowired
    private JedisPool jedisPool;

    @Around(value = "@annotation(com.chyl.mytest.ratelimiter.RateLimiter)")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        String methodName = pjp.getSignature().getName();
        RateLimiter rateLimiter = method.getAnnotation(RateLimiter.class);
        Object result;
        boolean res = tryAcquire(methodName, rateLimiter.limit(), rateLimiter.timeout());
        if (res) {
            result = pjp.proceed();
        } else {
            result = "";
        }
        return result;
    }

    public boolean tryAcquire(String key, Integer permits, Integer timeout) {
        //获取数量
        Jedis jedis = jedisPool.getResource();

        //判断
        return true;
    }


}
