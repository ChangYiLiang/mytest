package com.chyl.mytest.function;

import java.util.function.Consumer;

/**
 * consumer是一个有输入没输出的函数接口
 * @Author: chyl
 * @Date: 2019/6/13 14:43
 */
public class ConsumerTest {

    Consumer f0 = new Consumer() {
        @Override
        public void accept(Object o) {
            System.out.println(o + "");
        }
    };

    Consumer f1 = (a) -> {
        System.out.println(a + "");
    };

    Consumer f2 = o -> System.out.printf(o + "");

    Consumer f3 = System.out::println;

    public static void main(String[] args) {
        Consumer a = System.out::println;
        Consumer b = s -> System.out.println("sss");
        a.accept("abc");
        a.andThen(b).accept("123");
    }
}
