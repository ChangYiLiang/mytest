package com.chyl.mytest.async;

import com.chyl.mytest.BaseTest;
import com.chyl.mytest.service.AsyncService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author chyl
 * @create 2018-04-28 上午10:02
 */
public class AsyncTest extends BaseTest{

    @Autowired
    private AsyncService AsyncService;

    @Test
    public void test() {
        AsyncService.asyncTest();

    }
}
