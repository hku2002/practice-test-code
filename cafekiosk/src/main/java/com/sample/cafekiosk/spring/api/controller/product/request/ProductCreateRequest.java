package com.sample.cafekiosk.spring.api.controller.product.request;

import com.sample.cafekiosk.spring.api.domain.product.enumtype.ProductSellingStatus;
import com.sample.cafekiosk.spring.api.domain.product.enumtype.ProductType;
import com.sample.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {

    @NotNull(message = "상품 타입은 필수입니다.")
    private ProductType type;

    @NotNull(message = "상품 판매상태는 필수입니다.")
    private ProductSellingStatus sellingStatus;

    @NotBlank(message = "상품 이름은 필수입니다.")
    private String name;

    @Positive(message = "상품 가격은 0 보다 커야 합니다.")
    private int price;

    @Builder
    public ProductCreateRequest(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public ProductCreateServiceRequest toServiceRequest(ProductCreateRequest request) {
        return ProductCreateServiceRequest.builder()
                .type(request.getType())
                .name(request.getName())
                .price(request.getPrice())
                .sellingStatus(request.getSellingStatus())
                .build();

    }
}
