package com.chyl.mytest.kafka;

import com.chyl.mytest.BaseTest;
import com.chyl.mytest.kafka.provider.KafkaSender;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author chyl
 * @create 2018-06-06 下午7:13
 */
public class KafkaTest extends BaseTest{
    @Autowired
    private KafkaSender kafkaSender;

    @Test
    public void test() {
        kafkaSender.send("Kafka 222");
    }

}
