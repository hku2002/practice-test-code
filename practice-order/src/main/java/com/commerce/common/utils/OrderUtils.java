package com.commerce.common.utils;

import com.commerce.product.domain.Product;

import java.util.List;

public class OrderUtils {

    private OrderUtils() {}

    public static String makeOrderName(List<Product> products) {
        if (products.size() <= 1) return products.get(0).getProductName();
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(products.get(0).getProductName())
                .append(" 외 ")
                .append(products.size() - 1)
                .append(" 건")
                .toString();
    }

    public static int calculateTotalPrice(List<Product> products) {
        return products.stream()
                .mapToInt(Product::getPrice)
                .sum();
    }
}
