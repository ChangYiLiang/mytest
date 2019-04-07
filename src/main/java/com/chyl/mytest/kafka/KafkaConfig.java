package com.chyl.mytest.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chyl
 * @create 2018-06-06 下午5:22
 */
//@Configuration
//@EnableKafka
//public class KafkaConfig {
//
//    @Value("${spring.kafka.producer.bootstrap-servers}")
//    private String servers;
//
//    @Value("${spring.kafka.consumer.bootstrap-servers}")
//    private String servers1;
//    @Value("${spring.kafka.consumer.enable-auto-commit}")
//    private boolean enableAutoCommit;
//    @Value("${spring.kafka.consumer.auto-commit-interval}")
//    private String autoCommitInterval;
//    @Value("${spring.kafka.consumer.group-id}")
//    private String groupId;
//    @Value("${spring.kafka.consumer.auto-offset-reset}")
//    private String autoOffsetReset;
//
//
//    public Map<String, Object> producerConfigs() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        return props;
//    }
//
//    public ProducerFactory<String, String> producerFactory() {
//        return new DefaultKafkaProducerFactory<>(producerConfigs());
//    }
//
//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate() {
//        return new KafkaTemplate<String, String>(producerFactory());
//    }
//
//    @Bean
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        factory.getContainerProperties().setPollTimeout(1500);
//        return factory;
//    }
//
//    public ConsumerFactory<String, String> consumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
//    }
//
//
//    public Map<String, Object> consumerConfigs() {
//        Map<String, Object> propsMap = new HashMap<>();
//        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers1);
//        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
//        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
//        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
//        return propsMap;
//    }
//
//    @Bean
//    public KafkaProperties.Listener listener() {
//        return new KafkaProperties.Listener();
//    }
//
//}
