package com.chyl.mytest.Lambda;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author chyl
 * @create 2018-08-11 下午4:15
 */

public class Lambda {

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        list.add("111");
        list.add("222");
        list.add("333");
        list.forEach(a -> System.out.printf(a));
        list.forEach(a -> list2.add(a));
        System.out.println(list2.size());
    }

}
