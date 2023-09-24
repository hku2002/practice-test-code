package com.sample.cafekiosk.spring.api.controller.order;

import com.sample.cafekiosk.spring.api.common.response.BodyResponse;
import com.sample.cafekiosk.spring.api.common.response.CommonResponse;
import com.sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import com.sample.cafekiosk.spring.api.service.order.OrderService;
import com.sample.cafekiosk.spring.api.service.order.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public ResponseEntity<BodyResponse<OrderResponse>> createOrder(@RequestBody @Valid OrderCreateRequest request) {
        return CommonResponse.created(orderService.createOrder(request.toServiceRequest(request), LocalDateTime.now()));
    }
}
