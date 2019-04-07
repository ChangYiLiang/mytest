package com.chyl.mytest.redis;

import com.chyl.mytest.BaseTest;
import com.sun.tools.javac.util.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

/**
 * @author chyl
 * @create 2018-04-24 下午11:41
 */
public class RedisToolTest extends BaseTest{

    @Autowired
    private JedisPool jedisPool;

    @Test
    public void tryGetDistributedLock() {
        Jedis jedis = jedisPool.getResource();
        boolean res = RedisTool.releaseDistributedLock(jedis, "chylkk00", "454");
        System.out.println("res="+res);
    }

    @Test
    public void releaseDistributedLock() {
        Jedis jedis = jedisPool.getResource();
        boolean chyl = RedisTool.releaseDistributedLock(jedis, "chyl", "111");
        jedis.close();
    }

    @Test
    public void lockTest() {
        //防止重复提交
        Jedis jedis = jedisPool.getResource();
        String uuid = String.valueOf(Math.random());
        boolean res = false;
        try {
            res = RedisTool.releaseDistributedLock(jedis,"chyl110011",uuid);
            if (res) {
                System.out.println("执行");
            }
            else {
                System.out.println("重复提交"+"时间："+new Date().getTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            boolean b = false;
            if(res) {
                b = RedisTool.releaseDistributedLock(jedis, "chyl110011", uuid);
                if (b) {
                    System.out.println("释放锁成功");
                }else {
                    System.out.println("释放锁失败");
                }
            }
            jedis.close();
        }
    }

    @Test
    public void thread() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(100);
        final CountDownLatch cdOrder = new CountDownLatch(1);//指挥官的命令，设置为1，指挥官一下达命令，则cutDown,变为0，战士们执行任务
//        final CountDownLatch cdAnswer = new CountDownLatch(100);
        for (int i = 1; i <= 100; i++) {
            Runnable runnable = new Runnable(){
                @Override
                public void run() {
                    try {
                        cdOrder.await();
                        lockTest();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            service.execute(runnable);
        }
        cdOrder.countDown();
        System.out.println("发送执行命令");
        service.shutdown();
    }

    class MyThread implements Runnable {

        @Override
        public void run() {
            lockTest();
        }
    }
}