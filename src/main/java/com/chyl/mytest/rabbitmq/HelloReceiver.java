package com.chyl.mytest.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.annotation.ResponseJSONP;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.keyvalue.repository.config.QueryCreatorType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chyl
 * @create 2018-04-13 下午6:23
 */
@Component
public class HelloReceiver {

    @RabbitListener(queues = "topic.message")
    public String process1(String a) {

        System.out.println(a);
        Messages o = JSON.parseObject(a, Messages.class);

        return "success";
    }

//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(name = ""),
//            exchange = @Exchange(name = ""),
//            key = ""
//    ))
//    public String process2(String a) {
//        System.out.println("Receiver2====>>>>  : " + a);
//        return "success";
//    }

}