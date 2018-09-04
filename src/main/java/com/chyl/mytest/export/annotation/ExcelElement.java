package com.chyl.mytest.export.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author only-lilei
 * @create 2018-01-22 下午6:12
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExcelElement {

    /**
     *
     * 默认属性
     * @return  String 返回类型
     * @throws
     */
    String value() default "";
}

