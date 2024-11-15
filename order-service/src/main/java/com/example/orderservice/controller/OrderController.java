package com.example.orderservice.controller;

import com.example.orderservice.DTO.Order;
import com.example.orderservice.DTO.OrderEvent;
import com.example.orderservice.publisher.OrderProducer;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class OrderController {
    private OrderProducer orderProducer;

    @PostMapping("/order")
    public String order(@RequestBody Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        OrderEvent orderEvent = OrderEvent.builder()
                .status("PENDING")
                .message("Order is in Pending status")
                .order(order)
                .build();

        orderProducer.sendEvent(orderEvent);
        return "Event sent to RabbitMQ...";
    }
}
