package com.chyl.mytest.cache;

import com.chyl.mytest.util.SpringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @author chyl
 * @create 2018-09-05 上午11:21
 */
@Slf4j
@RestController
@RequestMapping("/guava/")
public class GuavaController {

    private User user;

    @GetMapping("user")
    public User getUser() {
        return user;
    }

    @GetMapping("user/cache/{id}")
    public User getCacheUser(@PathVariable String id) {
        GuavaController guavaController = (GuavaController)SpringUtil.getApplicationContext().getBean("guavaController");
        user.setName(guavaController.getUserName());
        return user;
    }

    @Cacheable(value = "user", key = "'getCacheUser'")
    public String getUserName() {
        log.info("====>>>>查询user");
        return "sss";
    }

    @PostConstruct
    public void init() {
        User newUser = new User();
        newUser.setName("aaa");
        newUser.setMoney(890098D);
        user = newUser;
    }


    @Data
    class User {
        private String name;
        private Double money;
    }
}
