package com.secondhand.api.product.dto;

import com.secondhand.api.product.domain.Product;
import com.secondhand.api.product.domain.enumtype.ProductStatus;
import com.secondhand.api.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCreateRequest {

    private Long id;
    private String name;
    private int price;
    private ProductStatus status;

    @Builder
    public ProductCreateRequest(Long id, String name, int price, ProductStatus status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public static Product of(ProductCreateRequest request, User user) {
        return Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .status(request.getStatus())
                .user(user)
                .build();
    }
}
