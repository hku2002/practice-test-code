package com.commerce.order.domain;

import com.commerce.order.domain.enumtype.OrderStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.commerce.order.domain.enumtype.OrderStatus.COMPLETED;
import static com.commerce.order.domain.enumtype.OrderStatus.CREATED;

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

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderProduct> orderProduct;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(mappedBy = "order", cascade = CascadeType.PERSIST)
    private Delivery delivery;

    @Builder
    public Order(Long id, String orderName, int totalPrice, List<OrderProduct> orderProduct, OrderStatus status, Delivery delivery) {
        this.id = id;
        this.orderName = orderName;
        this.totalPrice = totalPrice;
        this.orderProduct = orderProduct;
        this.status = status;
        this.delivery = delivery;
    }

    public void completeStatus() {
        if (this.status != CREATED) {
            throw new IllegalArgumentException("주문 완료처리가 불가능한 상태입니다.");
        }
        this.status = COMPLETED;

    }
}
