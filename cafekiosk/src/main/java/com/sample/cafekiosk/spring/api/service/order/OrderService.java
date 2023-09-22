package com.sample.cafekiosk.spring.api.service.order;

import com.sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import com.sample.cafekiosk.spring.api.domain.order.Order;
import com.sample.cafekiosk.spring.api.domain.order.OrderRepository;
import com.sample.cafekiosk.spring.api.domain.product.Product;
import com.sample.cafekiosk.spring.api.domain.product.ProductRepository;
import com.sample.cafekiosk.spring.api.service.order.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<Product> products = productRepository.findAllByProductNumberIn(request.getProductNumbers());
        Order order = Order.create(products, registeredDateTime);
        return OrderResponse.of(orderRepository.save(order));
    }
}
