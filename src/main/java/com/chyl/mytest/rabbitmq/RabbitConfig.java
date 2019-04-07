package com.chyl.mytest.rabbitmq;



import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chyl
 * @create 2018-04-13 下午6:00
 */
//@Configuration
public class RabbitConfig {

    @Value("${spring.rabbitmq.queueName1}")
    private String message = "topic.message";
    @Value("${spring.rabbitmq.queueName2}")
    private String messages = "topic.messages";
    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Bean
    public Queue queueMessage() {
        return new Queue(message, true);//true表示持久化该队列
    }

    @Bean
    public Queue queueMessages() {
        return new Queue(messages, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }

    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }
}
