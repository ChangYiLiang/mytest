package com.chyl.mytest.ratelimiter;

import java.lang.annotation.*;

/**
 * @author chyl
 * @create 2018-08-08 上午11:09
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {
    int limit() default 2;
    int timeout() default 1000;
}
