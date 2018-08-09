package com.chyl.mytest.interceptor;

import com.chyl.mytest.ratelimiter.RateLimiter;
import com.chyl.mytest.ratelimiter.RedisRateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author chyl
 * @create 2018-08-08 下午5:09
 */
@Configuration
@Slf4j
@Component
public class MyWebAppConfigurer  extends WebMvcConfigurerAdapter {

    @Autowired
    private JedisPool jedisPool;

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 多个拦截器组成一个拦截器链
//        // addPathPatterns 用于添加拦截规则
//        // excludePathPatterns 用户排除拦截
//        registry.addInterceptor(new WebInterceptor()).addPathPatterns("/**");
//        super.addInterceptors(registry);
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new WebInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new HandlerInterceptorAdapter() {
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                Method method = ((HandlerMethod) handler).getMethod();
                if (AnnotatedElementUtils.isAnnotated(method, RateLimiter.class)) {
                    RateLimiter rateLimiter = method.getAnnotation(RateLimiter.class);
                    int limit = rateLimiter.limit();
                    int timeout = rateLimiter.timeout();
                    Jedis jedis = jedisPool.getResource();
                    String token = RedisRateLimiter.acquireTokenFromBucket(jedis, limit, timeout);
                    if (token == null) {
                        response.sendError(500);
                        return false;
                    }
                    log.debug("token -> {}", token);
                    jedis.close();
                }
                return true;
            }
        }).addPathPatterns("/**");
    }

}
