package com.chyl.mytest.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

/**
 * @author chyl
 * @create 2018-04-28 上午9:57
 */
@Component
public class PoolAsyncConfigurer implements AsyncConfigurer {

    private static final Logger log = LoggerFactory.getLogger(PoolAsyncConfigurer.class);

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(5);//当前线程数
        threadPool.setMaxPoolSize(500);// 最大线程数
        threadPool.setQueueCapacity(1);//线程池所使用的缓冲队列
        threadPool.setWaitForTasksToCompleteOnShutdown(true);//等待任务在关机时完成--表明等待所有线程执行完
        threadPool.setAwaitTerminationSeconds(60 * 15);// 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
        threadPool.setThreadNamePrefix("PoolAsync-");//  线程名称前缀
        threadPool.initialize(); // 初始化
        return threadPool;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new PoolAsyncExceptionHandler();
    }

    /**
     * 自定义异常处理类
     * @author chyl
     */
    class PoolAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
            System.out.println("====>>>>捕获线程异常信息");
            log.error("Exception message - " + throwable.getMessage());
            log.error("Method name - " + method.getName());
            for (Object param : obj) {
                log.error("Parameter value - " + param);
            }
        }
    }
}
