package com.chyl.mytest.retry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author chyl
 * @create 2019-01-17 9:44 AM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RetryTest {
    @Autowired
    private RetryService retryService;

    @Test
    public void test() {
        retryService.updateName();
    }
}
