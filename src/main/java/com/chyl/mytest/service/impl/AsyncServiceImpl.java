package com.chyl.mytest.service.impl;

import com.chyl.mytest.http.HttpUtils;
import com.chyl.mytest.service.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author chyl
 * @create 2018-04-17 下午1:46
 */
@Service
public class AsyncServiceImpl implements AsyncService {

    @Async
    @Override
    public void asyncTest() {
        System.out.println("线程名称："+Thread.currentThread().getName() + " be ready to read data!");
//        HttpUtils.doPostForJson("http://1111.130.2.91:81/OrderCheck.aspx","123");
        throw new NullPointerException();
    }

    @Override
    public void normalTest() {
        HttpUtils.doPostForJson("http://10.130.2.91:81/OrderCheck.aspx","123");
    }
}
