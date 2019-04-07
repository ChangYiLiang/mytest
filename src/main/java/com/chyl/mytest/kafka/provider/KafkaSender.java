package com.chyl.mytest.kafka.provider;

import com.alibaba.fastjson.JSON;
import com.chyl.mytest.kafka.beans.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author chyl
 * @create 2018-06-06 下午6:57
 */
@Component
@Slf4j
public class KafkaSender {

    @Value("${spring.kafka.topic.test}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    //发送消息方法
    public void send(String messages) {
        Message message = new Message();
        message.setId(System.currentTimeMillis());
        message.setMsg(messages);
        message.setSendTime(new Date());
        String key = "myKey";
        log.info("+++++++++++++++++++++  message = {}", JSON.toJSONString(message));
        kafkaTemplate.send(topic,key,JSON.toJSONString(message));
    }
}
