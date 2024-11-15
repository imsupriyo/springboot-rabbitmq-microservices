package com.example.emailservice.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderEvent {
    private String status;
    private String message;
    private Order order;
}
