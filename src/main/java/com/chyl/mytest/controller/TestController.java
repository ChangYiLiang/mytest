package com.chyl.mytest.controller;

import com.chyl.mytest.http.HttpUtils;
import com.chyl.mytest.kafka.provider.KafkaSender;
import com.chyl.mytest.ratelimiter.RateLimiter;
import com.chyl.mytest.redis.RedisService;
import com.chyl.mytest.service.AsyncService;
import com.chyl.mytest.util.IpAdrressUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chyl
 * @create 2018-04-11 下午1:31
 */
@RestController
@RequestMapping("/controller/")
public class TestController {

    private static ConcurrentHashMap count = new ConcurrentHashMap();

    @Autowired
    private KafkaSender kafkaSender;

    @Autowired
    private AsyncService asyncService;
    @Autowired
    private RedisService redisService;


    @GetMapping("/test")
    public String test() {
        redisService.set("123", "321");
        System.out.println(this.getClass().getMethods());
        return "success";
    }

    @PostMapping("/test3")
//    @RateLimiter(limit = 5)
    public String test3() {
        count.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        System.out.println("请求次数====>>>>"+count.size() + "-"+System.currentTimeMillis());
        return "success";
    }

    @PostMapping("/test5")
    @RateLimiter(limit = 20)
    public String test5() {
        count.clear();
        return "success";
    }



    @GetMapping("/test6")
    public String test6(HttpServletRequest request) {
        System.out.println(IpAdrressUtil.getIpAdrress(request));
        System.out.println(request.getRemoteAddr());
        return "success";
    }


    @GetMapping("/test8")
    public String test8() {
       kafkaSender.send("Kafka 123");
        return "success";
    }

    public void postHttp(String user) {
//        HttpUtils.doPostForJson("http://10.130.2.91:81/OrderCheck.aspx","123");
        System.out.println("Async");
    }


    public void getHttp(String user) {
        System.out.println("同步");
    }

    public static void main(String[] args) {
//        String s = HttpUtils.doPostForJson("http://127.0.0.1:8888/controller/test3", "");
//        System.out.println(s);
    }

}
