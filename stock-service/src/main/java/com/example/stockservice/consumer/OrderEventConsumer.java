package com.example.stockservice.consumer;

import com.example.stockservice.DTO.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {
    private final static Logger LOGGER = LoggerFactory.getLogger(OrderEventConsumer.class);

    @RabbitListener(queues = {"${rabbitmq.queue.stock.name}"})
    public void receiveEvent(OrderEvent orderEvent) {
        LOGGER.info("Received event => {}", orderEvent);
        // store the order in database
    }
}
