package com.example.pizza.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id" , nullable = false)
    private Long Id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "price_at_purchase")
    private BigDecimal priceAtPurchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "order_item__extra_ingredients",
    joinColumns = @JoinColumn(name = "order_item_id"),
    inverseJoinColumns = @JoinColumn(name = "extraingredient_id"))
    private List<ExtraIngredient> extraIngredientsList;
}

