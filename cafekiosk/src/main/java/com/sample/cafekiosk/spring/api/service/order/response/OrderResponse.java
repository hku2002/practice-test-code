package com.sample.cafekiosk.spring.api.service.order.response;

import com.sample.cafekiosk.spring.api.domain.order.Order;
import com.sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderResponse {

    private Long id;
    private int totalPrice;
    private LocalDateTime registeredDateTime;
    private List<ProductResponse> products;

    @Builder
    public OrderResponse(Long id, int totalPrice, LocalDateTime registeredDateTime, List<ProductResponse> products) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.registeredDateTime = registeredDateTime;
        this.products = products;
    }

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .registeredDateTime(order.getRegisteredDateTime())
                .products(order.getOrderProducts()
                        .stream()
                        .map(orderProduct -> ProductResponse.from(orderProduct.getProduct()))
                        .collect(Collectors.toList())
                )
                .build();
    }
}
