package com.commerce.order.domain;

import com.commerce.order.domain.enumtype.OrderStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderName;

    private int totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(mappedBy = "order", cascade = CascadeType.PERSIST)
    private Delivery delivery;

    @Builder
    public Order(Long id, String orderName, int totalPrice, OrderStatus status, Delivery delivery) {
        this.id = id;
        this.orderName = orderName;
        this.totalPrice = totalPrice;
        this.status = status;
        this.delivery = delivery;
    }
}
