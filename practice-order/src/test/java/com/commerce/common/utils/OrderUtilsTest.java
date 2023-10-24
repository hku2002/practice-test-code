package com.commerce.common.utils;

import com.commerce.product.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderUtilsTest {

    @Test
    @DisplayName("주문명이 외 x 건 형식으로 올바르게 만들어 진다.")
    void makeOrderNameTest() {
        // given
        Product product1 = Product.builder()
                .productName("상품명1")
                .price(1000)
                .quantity(10)
                .build();
        Product product2 = Product.builder()
                .productName("상품명2")
                .price(2000)
                .quantity(5)
                .build();
        List<Product> products = List.of(product1, product2);

        // when
        String orderName = OrderUtils.makeOrderName(products);

        // then
        assertThat(orderName).isEqualTo("상품명1 외 1 건");
    }

    @Test
    @DisplayName("상품이 1개면 상품명만 return 된다.")
    void productSize1Test() {
        // given
        Product product1 = Product.builder()
                .productName("상품명1")
                .price(1000)
                .quantity(10)
                .build();
        List<Product> products = List.of(product1);

        // when
        String orderName = OrderUtils.makeOrderName(products);

        // then
        assertThat(orderName).isEqualTo("상품명1");
    }

    @Test
    @DisplayName("총 가격이 올바르게 계산된다.")
    void totalPriceCalculateTest() {
        // given
        Product product1 = Product.builder()
                .productName("상품명1")
                .price(1000)
                .quantity(10)
                .build();
        Product product2 = Product.builder()
                .productName("상품명2")
                .price(2000)
                .quantity(5)
                .build();
        List<Product> products = List.of(product1, product2);

        // when
        int totalPrice = OrderUtils.calculateTotalPrice(products);

        // then
        assertThat(totalPrice).isEqualTo(3000);
    }

}
