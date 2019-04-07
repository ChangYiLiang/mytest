package com.chyl.mytest.retry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 * @author chyl
 * @create 2019-01-17 9:39 AM
 */
@Slf4j
@Service
public class RetryService {

    @Retryable(value = RuntimeException.class, backoff = @Backoff(delay = 5000L))
    public void updateName() {
        log.info("进入updateName方法:{}", System.currentTimeMillis());
        throw new RuntimeException();
    }

    @Recover
    public void recover(Exception e) {
        e.printStackTrace();
        log.error("进入recover方法：{}", e);
    }
}
