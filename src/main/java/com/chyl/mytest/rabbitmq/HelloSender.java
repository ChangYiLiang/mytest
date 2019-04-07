package com.chyl.mytest.rabbitmq;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
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
        Message message1 = new Message();
        message1.setName(message);
        message1.setTitle("MQ");
        String request = JSON.toJSONString(message1);
        Object response =  rabbitTemplate.convertSendAndReceive("exchange", "topic.message", request);
        System.out.printf(response.toString());
    }

    public void send2(String message) {
        System.out.println("Sender2 : " + message);
        this.rabbitTemplate.convertAndSend("exchange", "topic.messages", message);
    }
}
