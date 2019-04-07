package com.chyl.mytest;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author chyl
 * @create 2018-04-09 下午2:32
 */
@Component
@Order(0)
public class Initialize implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("初始化了");
    }
}
