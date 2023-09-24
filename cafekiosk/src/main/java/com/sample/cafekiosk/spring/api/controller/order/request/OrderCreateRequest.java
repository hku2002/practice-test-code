package com.sample.cafekiosk.spring.api.controller.order.request;

import com.sample.cafekiosk.spring.api.service.order.request.OrderCreateServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {

    @NotEmpty(message = "상품 번호는 1개 이상 등록되어야 합니다.")
    private List<String> productNumbers;

    @Builder
    public OrderCreateRequest(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }

    public OrderCreateServiceRequest toServiceRequest(OrderCreateRequest request) {
        return OrderCreateServiceRequest.builder()
                .productNumbers(request.getProductNumbers())
                .build();
    }

}
