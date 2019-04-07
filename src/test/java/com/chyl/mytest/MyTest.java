package com.chyl.mytest;

/**
 * @author chyl
 * @create 2018-05-23 下午4:15
 */
public class MyTest {

    public static void main(String[] args) {
       for (int i = 1; i < 10; i++) {
           Runnable haha = new Runnable() {
               @Override
               public void run() {
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   System.out.println("haha");
               }
           };
           haha.run();
       }

    }
}
