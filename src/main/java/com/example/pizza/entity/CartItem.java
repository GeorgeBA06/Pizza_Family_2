package com.example.pizza.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.support.ManagedArray;

@Entity
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_items")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_items_ids")
    private Item item;

}
