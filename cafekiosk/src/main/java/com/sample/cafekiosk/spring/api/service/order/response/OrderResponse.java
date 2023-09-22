package com.sample.cafekiosk.spring.api.service.order.response;

import com.sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderResponse {

    private Long id;
    private int totalPrice;
    private LocalDateTime registeredDateTime;
    private List<ProductResponse> products;
}
