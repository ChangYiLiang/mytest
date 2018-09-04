package com.chyl.mytest.Lambda;

import com.chyl.mytest.util.DateUtil;
import lombok.Data;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * @author chyl
 * @create 2018-09-03 下午1:56
 */
public class StreamTest {

    private List<User> users = new ArrayList<>();



    @Test
    public void stream() {
        ArrayList<User> users1 = new ArrayList<>();
        users.stream().filter(user -> !user.getName().equals("aaa"))
                .sorted(Comparator.comparing(User::getMoney)).
                forEach(u -> users1.add(u));


        System.out.println(users.size());
    }

    @Data
    class User {
        private String name;
        private Double money;
        private Long createTime;
    }

    @Before
    public void init () {
        long now = DateUtil.dateFormatUnix(new Date());
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();

        user1.setName("aaa");
        user2.setName("bbb");
        user3.setName("ccc");
        user4.setName("ddd");
        user1.setMoney(100D);
        user2.setMoney(800D);
        user3.setMoney(200D);
        user4.setMoney(600D);
        user1.setCreateTime(now);
        user2.setCreateTime(now+100);
        user3.setCreateTime(now+800);
        user4.setCreateTime(now+200);

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

    }
}
