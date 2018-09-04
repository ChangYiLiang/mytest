package com.chyl.mytest.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author chyl
 * @create 2018-08-21 下午10:59
 * jdk动态代理（基于接口实现）
 */
@Slf4j
public class MyHandle implements InvocationHandler{

    private Object target;

    public MyHandle(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().contains("eat")) {
            log.info(method.getName()+"被代理");
        }
        return method.invoke(target,args);
    }

    public static void main(String[] args) {
        MyHandle myHandle = new MyHandle(new UserServiceImpl());
        UserService userService = (UserService) Proxy.newProxyInstance(MyHandle.class.getClassLoader(), new Class[]{UserService.class}, myHandle);
//        userService.eatApple();
//        userService.eatBanana();
//        userService.sleep();
        userService.run();
    }
}
