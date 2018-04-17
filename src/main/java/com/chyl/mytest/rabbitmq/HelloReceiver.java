package com.chyl.mytest.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author chyl
 * @create 2018-04-13 下午6:23
 */
@Component
public class HelloReceiver {

    @RabbitListener(queues = "topic.message")
    public String process1(String a) {
        System.out.println("Receiver1====>>>>  : " + a);
        return "success";
    }

//    @RabbitListener(queues = "topic.messages")
//    public String process2(String a) {
//        System.out.println("Receiver2====>>>>  : " + a);
//        return "success";
//    }

}