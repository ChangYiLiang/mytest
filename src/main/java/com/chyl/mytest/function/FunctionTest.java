package com.chyl.mytest.function;

import java.util.function.Function;

/**
 * function是一个有输入有输出的函数接口
 * @Author: chyl
 * @Date: 2019/6/13 16:30
 */
public class FunctionTest {
    Function<String, String> fu = s -> s + "ok";

    Function<Integer, Integer> f1 =s -> s * 2;

    Function<Integer, Integer> f2 =s -> s + 1;

    public static void main(String[] args) {
        Function<Integer, Integer> f1 =s -> s * 2;
        Function<Integer, Integer> f2 =s -> s + 1;
        Integer apply = f1.apply(2);
        System.out.println(apply);
        Integer apply1 = f1.compose(f2).apply(3);
        System.out.println(apply1);
        Integer apply2 = f1.andThen(f2).apply(3);
        System.out.println(apply2);
        Function<Integer, Integer> f3 =Function.identity();
        Integer apply3 = f3.apply(10);
        System.out.println(apply3);
    }
}
