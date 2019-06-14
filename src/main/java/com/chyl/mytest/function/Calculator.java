package com.chyl.mytest.function;

import java.lang.reflect.Array;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @Author: chyl
 * @Date: 2019/6/13 19:07
 */
public class Calculator {

    public static void main(String[] args) {
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        BiFunction<Integer, Integer, Integer> sub = (a, b) -> a - b;
        BiFunction<Integer, Integer, Integer> mult = (a, b) -> a * b;
        BiFunction<Integer, Integer, Integer> div = (a, b) -> a / b;

        Integer apply = add.apply(1, 4);
        System.out.printf(apply + "");
    }
}
