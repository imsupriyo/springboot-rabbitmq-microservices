package com.example.emailservice.DTO;

import lombok.Data;

@Data
public class Order {
    private String orderId;
    private String name;
    private int qty;
    private double price;
}

