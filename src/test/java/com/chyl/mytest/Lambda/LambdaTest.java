package com.chyl.mytest.Lambda;

import lombok.Data;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author chyl
 * @create 2018-08-31 下午3:28
 */
public class LambdaTest {


    @Before
    public void initDate() {


    }

    @Test
    public void forEach() {
        User user = new User();
        user.setName("aaa");
        user.setAge("12");

        User user1 = new User();
        user1.setName("bbb");
        user1.setAge("18");

        ArrayList<User> users = new ArrayList<>();
//        users.add(user);
//        users.add(user1);

//        users.forEach(u -> System.out.println(u.name));
        for (User u : users) {
           u.setName("222");
        }

    }

    @Data
    class User {
        private String name;
        private String age;
    }
}
