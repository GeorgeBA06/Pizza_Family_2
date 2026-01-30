package com.example.pizza.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
@Table(name = "orders")
public abstract class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "order_items", nullable = false)
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

//    @Column(name = "orderItems", nullable = false)
//    @OneToMany(mappedBy = "order")
//    private Set<OrderItem> orderItemsSet = new LinkedHashSet<>();

    @Column(name = "total_price", precision = 19, scale = 4)
    private BigDecimal totalPrice;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.ORDER_RECEIVED;

    @Column(name = "paytype")
    @Enumerated(EnumType.STRING)
    private PayType payType;
}
