package com.sample.cafekiosk.spring.api.domain.product.enumtype;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum ProductType {

    HANDMADE("제조 음료"),
    BOTTLE("병 음료"),
    BAKERY("베이커리");

    private final String text;
    public static boolean containsStockManagedType(ProductType type) {
        return List.of(BOTTLE, BAKERY).contains(type);
    }
}
