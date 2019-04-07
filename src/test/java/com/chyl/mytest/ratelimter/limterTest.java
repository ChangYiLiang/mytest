package com.chyl.mytest.ratelimter;

import com.chyl.mytest.http.HttpUtils;
import org.junit.Test;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author chyl
 * @create 2018-08-08 下午1:25
 */
public class limterTest {

    @Test
    public void limit() throws InterruptedException {
        for (int i = 0; i < 300; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
//                    String s = HttpUtils.doPostForJson("http://127.0.0.1:8888/controller/test3", "");
//                    System.out.println(s);
                }
            };
            thread.start();
            thread.join();
        }
    }

    @Test
    public void limit2() {

    }

    public static void main(String[] args) {
//        String s = HttpUtils.doPostForJson("http://127.0.0.1:8888/controller/test5", "");
        Integer taskCount = 2000;
        // 锁住所有线程，等待并发执行
        final CountDownLatch begin = new CountDownLatch(1);
        final ExecutorService exec = Executors.newFixedThreadPool(taskCount);
        for (int index = 0; index < taskCount; index++) {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        begin.await();
//                        String s = HttpUtils.doPostForJson("http://127.0.0.1:8888/controller/test3", "");
//                        System.out.println(s);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            exec.submit(run);
        }
        System.out.println("开始执行");
        begin.countDown();
        //关闭执行
        exec.shutdown();
    }
}
