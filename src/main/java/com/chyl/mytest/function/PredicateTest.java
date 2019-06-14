package com.chyl.mytest.function;

import java.util.function.Predicate;

/**
 * @Author: chyl
 * @Date: 2019/6/13 18:36
 */
public class PredicateTest {
    Predicate<String> p = p -> p.equals("abc");
    Predicate<Integer> p1 = p -> p > 0;

    public static void main(String[] args) {
        Predicate<String> p = x -> x.equals("abc");
        Predicate<Integer> p1 = x -> x > 0;
        Predicate<Integer> p2 = x -> x == 2;
        boolean res = p.test("abc");
        System.out.printf(res + "");
        boolean test = p1.and(p2).test(2);
        System.out.printf("" + test);
    }
}
