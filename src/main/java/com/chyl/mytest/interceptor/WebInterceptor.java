package com.chyl.mytest.interceptor;

import com.chyl.mytest.ratelimiter.RateLimiter;
import com.chyl.mytest.ratelimiter.RedisRateLimiter;
import com.chyl.mytest.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author chyl
 * @create 2018-08-08 上午11:39
 */

@Slf4j
@Component
public class WebInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JedisPool jedisPool;
//    private JedisPool jedisPool = SpringUtil.getBean(JedisPool.class);

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
////        Method method = ((HandlerMethod) handler).getMethod();
////        if (AnnotatedElementUtils.isAnnotated(method, RateLimiter.class)) {
////            RateLimiter rateLimiter = method.getAnnotation(RateLimiter.class);
////            int limit = rateLimiter.limit();
////            int timeout = rateLimiter.timeout();
////            Jedis jedis = jedisPool.getResource();
////            String token = RedisRateLimiter.acquireTokenFromBucket(jedis, limit, timeout);
////            if (token == null) {
////                response.sendError(500);
////                return false;
////            }
////            log.debug("token -> {}", token);
////            jedis.close();
////        }
////        return true;
//    }
}
