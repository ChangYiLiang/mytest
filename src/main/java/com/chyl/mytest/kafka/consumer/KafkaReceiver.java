package com.chyl.mytest.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author chyl
 * @create 2018-06-06 下午7:06
 */
@Component
@Slf4j
public class KafkaReceiver {

    @KafkaListener(topics = "${spring.kafka.topic.test}")
    public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            System.out.printf("====>>>>收到消息了");
            log.info("-----------------  =" + record);
            log.info("------------------ message =" + message);
        }
    }

//    @KafkaListener(topics = {"test"})
//    public void listen(ConsumerRecord<?, ?> record) {
//        log.info("kafka的key: " + record.key());
//        log.info("kafka的value: " + record.value().toString());
//    }
}