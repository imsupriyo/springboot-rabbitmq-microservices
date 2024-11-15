package com.example.orderservice.publisher;

import com.example.orderservice.DTO.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    private final static Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String orderExchange;

    @Value("${rabbitmq.queue.stock.route-key}")
    private String orderStockRouteKey;
    @Value("${rabbitmq.queue.email.route-key}")
    private String orderEmailRouteKey;

    public OrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEvent(OrderEvent orderEvent) {
        LOGGER.info("sending message => {}", orderEvent);
        // send event to stock queue
        rabbitTemplate.convertAndSend(orderExchange, orderStockRouteKey, orderEvent);

        // send event to email queue
        rabbitTemplate.convertAndSend(orderExchange, orderEmailRouteKey, orderEvent);
    }
}
