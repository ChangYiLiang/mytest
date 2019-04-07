package com.chyl.mytest.redis;

import com.chyl.mytest.mo.UserMO;
import org.springframework.web.bind.annotation.*;

/**
 * @author chyl
 * @create 2018-09-12 下午9:00
 */
@RestController
@RequestMapping("redis/controller/")
public class RedisController {

    @RedisLock(key = "#userMO.name")
    @PostMapping("/test")
    public String test(@RequestBody UserMO userMO) {
        return userMO.getName();
    }
}
