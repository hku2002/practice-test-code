package com.commerce.order.domain;

import com.commerce.product.domain.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private String productName;

    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @Builder
    public OrderProduct(Long id, Long productId, String productName, int price, Order order) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.order = order;
    }

    public static OrderProduct createOrderProduct(Product product, Order order) {
        return OrderProduct.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .order(order)
                .build();
    }
}
