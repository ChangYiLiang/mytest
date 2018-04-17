package com.chyl.mytest.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author chyl
 * @create 2018-04-13 下午6:22
 */
@Component
public class HelloSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send1(String message) {
        System.out.println("Sender1 : " + message);
        Object response =  rabbitTemplate.convertSendAndReceive("exchange", "topic.message", message);
        System.out.printf(response.toString());
    }

    public void send2(String message) {
        System.out.println("Sender2 : " + message);
        this.rabbitTemplate.convertAndSend("exchange", "topic.messages", message);
    }
}
