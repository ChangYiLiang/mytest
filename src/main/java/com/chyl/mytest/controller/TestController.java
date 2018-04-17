package com.chyl.mytest.controller;

import com.chyl.mytest.http.HttpUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chyl
 * @create 2018-04-11 下午1:31
 */
@RestController
@RequestMapping("/controller/")
public class TestController {

    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        getHttp("user");
        return "success";
    }

    private void getHttp(String user) {
        HttpUtils.doGet("www.123.ccc.cc");
    }
}
