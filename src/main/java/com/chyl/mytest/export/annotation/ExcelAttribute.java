package com.chyl.mytest.export.annotation;

import java.lang.annotation.*;

/**
 * @author only-lilei
 * @create 2018-01-22 下午6:09
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExcelAttribute {

    /**
     * 导出到Excel中的名字.
     */
    String title();

    /**
     * 配置列的顺序,从0开始 0,1,2,3,4
     */
    int column();


}
