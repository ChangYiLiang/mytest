package com.chyl.mytest.proxy;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

/**
 * @author chyl
 * @create 2018-08-21 下午10:58
 */
public class UserServiceImpl implements UserService {
    @Override
    public void eatApple() {
        System.out.println("eatApple");
    }

    @Override
    public void eatBanana() {
        System.out.println("eatBanana");
    }

    @Override
    public void sleep() {
        System.out.println("sleep");
    }

    @Override
    public void run() {
        UserService userService = (UserService) AopContext.currentProxy();
        userService.eatApple();
    }
}
