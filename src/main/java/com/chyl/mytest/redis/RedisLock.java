package com.chyl.mytest.redis;

import java.lang.annotation.*;

/**
 * @author Tomdy
 * @create 2018-08-15 下午1:54
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RedisLock {
    String key() default "";
}
