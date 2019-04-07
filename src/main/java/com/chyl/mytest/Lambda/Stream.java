package com.chyl.mytest.Lambda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author chyl
 * @create 2018-09-05 下午5:10
 */
public class Stream {

    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        ArrayList<Integer> list2 = new ArrayList<>();
        ArrayList<Integer> list3 = new ArrayList<>();

        for (int i = 0; i<10000;i++) {
            list.add(i);
        }
        long start = System.currentTimeMillis();
        for (Integer n : list) {
            list2.add(n);
        }
        long end = System.currentTimeMillis();
        System.out.println("1-处理时间：" + (end - start) + "msc");
        long start2 = System.currentTimeMillis();
        list.stream().parallel().forEach(l ->list3.add(l));
        long end2 = System.currentTimeMillis();
        System.out.println("2-处理时间：" + (end2 - start2) + "msc");

        System.out.println(list2.size());
        System.out.println(list3.size());
    }
}
