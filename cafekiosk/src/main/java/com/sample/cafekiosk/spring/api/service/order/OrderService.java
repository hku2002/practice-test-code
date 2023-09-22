package com.sample.cafekiosk.spring.api.service.order;

import com.sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import com.sample.cafekiosk.spring.api.domain.product.ProductRepository;
import com.sample.cafekiosk.spring.api.service.order.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;

    public OrderResponse createOrder(OrderCreateRequest request) {
        List<String> productNumbers = request.getProductNumbers();
        return null;
    }
}
