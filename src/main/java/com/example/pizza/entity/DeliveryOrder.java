package com.example.pizza.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import org.springframework.stereotype.Component;

@Entity
@Data
public class DeliveryOrder extends Order {

    @Column(name = "order_address", length = 100)
    private String address;
}
