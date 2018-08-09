package com.chyl.mytest.controller;


import com.chyl.mytest.http.HttpUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

/**
 * @author chyl
 * @create 2018-04-17 上午11:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestControllerTest {


    @Test
    public void test() {
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        HttpUtils.doGet("http://127.0.0.1:8888/controller/test");
    }
}