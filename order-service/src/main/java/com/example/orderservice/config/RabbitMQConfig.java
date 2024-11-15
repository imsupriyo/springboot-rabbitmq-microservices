package com.example.orderservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${rabbitmq.exchange.name}")
    private String orderExchange;

    @Value("${rabbitmq.queue.stock.name}")
    private String orderStockQueue;
    @Value("${rabbitmq.queue.stock.route-key}")
    private String orderStockRouteKey;

    @Value("${rabbitmq.queue.email.name}")
    private String orderEmailQueue;
    @Value("${rabbitmq.queue.email.route-key}")
    private String orderEmailRouteKey;


    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(orderExchange);
    }

    @Bean
    public Queue stockQueue() {
        return new Queue(orderStockQueue);
    }

    @Bean
    public Binding stockQueueBinding() {
        return BindingBuilder
                .bind(stockQueue())
                .to(topicExchange())
                .with(orderStockRouteKey);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(orderEmailQueue);
    }

    @Bean
    public Binding emailQueueBinding() {
        return BindingBuilder
                .bind(emailQueue())
                .to(topicExchange())
                .with(orderEmailRouteKey);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
