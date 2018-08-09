package com.chyl.mytest.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author chyl
 * @create 2018-04-24 下午2:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceImplTest {

    @Autowired
    private RedisService redisService;


    @Test
    public void get() {
        String s = redisService.get("222");
        System.out.printf(s);
    }

    @Test
    public void set() {

    }

    @Test
    public void get1() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void exists() {
        Boolean exists = redisService.exists("123");
        System.out.printf("result:"+String.valueOf(exists));
    }

    @Test
    public void expire() {
    }

    @Test
    public void setnx() {
       redisService.setex("chyl098", 60*60L, "666");

    }

    @Test
    public void setex() {
        Boolean setnx = redisService.setnx("111", "666");
        System.out.printf(String.valueOf(setnx));
    }

    @Test
    public void setrange() {
    }

    @Test
    public void getrange() {
    }

    @Test
    public void mset() {

    }

    @Test
    public void mget() {
    }
}