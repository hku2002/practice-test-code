package com.secondhand.api.product.dto;

import com.secondhand.api.product.domain.Product;
import com.secondhand.api.product.domain.enumtype.ProductStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {

    private String name;
    private int price;
    private ProductStatus status;

    @Builder
    public ProductResponse(String name, int price, ProductStatus status) {
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .status(product.getStatus())
                .build();
    }
}
